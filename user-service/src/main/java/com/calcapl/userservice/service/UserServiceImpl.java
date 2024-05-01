package com.calcapl.userservice.service;

import com.calcapl.userservice.collection.ActivationToken;
import com.calcapl.userservice.collection.AuthenticationToken;
import com.calcapl.userservice.collection.User;
import com.calcapl.userservice.common.AuthenticationRequest;
import com.calcapl.userservice.common.AuthenticationResponse;
import com.calcapl.userservice.common.CommonFunctions;
import com.calcapl.userservice.config.jwt.JwtService;
import com.calcapl.userservice.dto.FullUserDTO;
import com.calcapl.userservice.dto.UserDTO;
import com.calcapl.userservice.exception.NotFoundException;
import com.calcapl.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final CommonFunctions commonFunctions;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public ResponseEntity<?> userRegister(UserDTO userDTO) {
        Optional<User> emailCondition = userRepository.findByEmail(userDTO.getEmail());
        if(emailCondition.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("email already exists");
        }

        // ASSIGN USER DATA TO THE USER OBJECT
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .age(calculateAge(userDTO.getBirthDay()))
                .birthDay(userDTO.getBirthDay())
                .isActive(false)
                .build();

        // ENCODE PASSWORD
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);

        // CALLING EMAIL SERVICE
        boolean status = sendActivationMain(user);

        if(status){
            userRepository.save(user);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email service error orchard. try again");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("user registered successfully");
    }

    // METHOD TO CALCULATE AGE
    private int calculateAge(String birthDay) {
        LocalDate birthDate = LocalDate.parse(birthDay);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    @Override
    public ResponseEntity<?> activate(String token) {
        Optional<User> optionalUser = userRepository.findByActivationTokenToken(token);

        // USER NOT FOUND EXCEPTION
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user account not found");
        }
        User user = optionalUser.get();

        // CHECK ACTIVATION TOKEN EXPIRY
        LocalDateTime activationTokenExpiry = user.getActivationToken().getTokenExpiry();
        if (activationTokenExpiry.isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("activation token has expired");
        }

        user.setIsActive(true);
        userRepository.save(user);

        return ResponseEntity.ok().body("user activated successfully");
    }

    @Override
    public ResponseEntity<?> login(AuthenticationRequest request) {
        Optional<User> userCondition = userRepository.findByEmail(request.getEmail());

        // CHECK ACCOUNT STATUS
        if(userCondition.isPresent() && !userCondition.get().getIsActive()){
            boolean status = sendActivationMain(userCondition.get());
            if(status){
                userRepository.save(userCondition.get());
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email service error orchard. try again");
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("your account is not activated. Please check your email to verify your account first.");
        }

        // SPRING AUTHENTICATION MANAGER
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // ASSIGN USER DATA
        User user = new User();
        if(userCondition.isPresent()){
            user = userCondition.get();
        }

        // GENERATE AND SAVE ACCESS TOKEN
        String jwtToken = jwtService.generateToken(user);
        saveToken(user, jwtToken);

        // GENERATE REFRESH TOKEN
        String refreshToken = jwtService.generateRefreshToken(user);

        return ResponseEntity.ok().body(AuthenticationResponse.builder()
                .message("user authenticated successfully")
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build()
        );
    }

    // METHOD TO GENERATE ACTIVATION TOKEN AND SEND ACTIVATION MAIL
    private boolean sendActivationMain(User user) {
        // GENERATE ACTIVATION TOKEN
        String token = UUID.randomUUID().toString().substring(0, 32);

        // Activation link valid for 10 mints
        LocalDateTime tokenExpiry = LocalDateTime.now().plusMinutes(10);
        ActivationToken activationToken = ActivationToken.builder()
                .token(token)
                .tokenExpiry(tokenExpiry)
                .build();

        user.setActivationToken(activationToken);

        // CALLING EMAIL SERVICE
        return emailService.sendActivationMail(user);
    }

    // METHOD TO SAVE TOKEN INFORMATION
    private void saveToken(User user, String jwtToken) {
        AuthenticationToken authenticationToken = AuthenticationToken.builder()
                .accessToken(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        user.setAuthenticationToken(authenticationToken);

        userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> getUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        // USER NOT FOUND EXCEPTION
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user account not found");
        }
        User user = optionalUser.get();

        // ASSIGN USER DETAILS
        FullUserDTO fullUserDTO = FullUserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .birthDay(user.getBirthDay())
                .build();
        if(user.getDisorderTypes() != null){
            fullUserDTO.setDisorderTypes(user.getDisorderTypes());
        }

        return ResponseEntity.ok().body(fullUserDTO);
    }

    @Override
    public ResponseEntity<?> updateDetails(String name, String birthDay) throws NotFoundException {
        User user = commonFunctions.getUser();
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user account not found");
        }

        if(!name.isEmpty()){
            user.setName(name);
        }
        if(birthDay.isEmpty()){
            user.setBirthDay(birthDay);
            user.setAge(calculateAge(birthDay));
        }

        userRepository.save(user);

        return ResponseEntity.ok().body("user details updated successfully");
    }

    @Override
    public ResponseEntity<?> updateDisorderTypes(String disorderTypes) throws NotFoundException {
        User user = commonFunctions.getUser();
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user account not found");
        }

        if(!disorderTypes.isEmpty()){
            user.setDisorderTypes(disorderTypes);
        }

        userRepository.save(user);

        return ResponseEntity.ok().body("user disorder types updated successfully");
    }

    @Override
    public ResponseEntity<?> resetPassword(AuthenticationRequest request) {
        Optional<User> userCondition = userRepository.findByEmail(request.getEmail());

        // INVALID USER EXCEPTION
        if(userCondition.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("provided email address is invalid");
        }
        User user = userCondition.get();

        // ENCODE PASSWORD USING PASSWORD-ENCODER
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return ResponseEntity.ok().body("password reset successfully");
    }

    @Override
    public ResponseEntity<?> refreshToken(String refreshToken) {
        final String userEmail;
        userEmail = jwtService.extractUsername(refreshToken);

        // INVALID TOKEN EXCEPTION
        if(userEmail.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("the token you provided is invalid");
        }

        // INVALID ACCOUNT EXCEPTION
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent() && !optionalUser.get().getIsActive()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("your account is not activated yet");
        }

        // INVALID USER EXCEPTION
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user account not found");
        }
       User user = optionalUser.get();

        // GENERATE TOKEN
        AuthenticationResponse authResponse = null;
        if (jwtService.isTokenValid(refreshToken, user)) {
            String newAccessToken = jwtService.generateToken(user);
            saveToken(user, newAccessToken);

            String newRefreshToken = jwtService.generateRefreshToken(user);

            authResponse = AuthenticationResponse.builder()
                    .message("using refresh token user authenticated successfully")
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        }

        // SERVER ERROR EXCEPTION
        if(authResponse == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong with generating the token");
        }
        return ResponseEntity.ok().body(authResponse);
    }

    @Override
    public ResponseEntity<?> deactivate() throws NotFoundException {
        User user = commonFunctions.getUser();
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user account not found");
        }

        user.setIsActive(false);
        userRepository.save(user);

        return ResponseEntity.ok().body("user account deactivated successfully");
    }

    @Override
    public ResponseEntity<?> logout() throws NotFoundException {
        String jwt = commonFunctions.getToken();
        Optional<User> optionalUser = userRepository.findByAuthenticationTokenAccessToken(jwt);

        if(optionalUser.isPresent()){
            AuthenticationToken authenticationToken = optionalUser.get().getAuthenticationToken();
            authenticationToken.setExpired(true);
            authenticationToken.setRevoked(true);

            User user = optionalUser.get();
            user.setAuthenticationToken(authenticationToken);
            userRepository.save(user);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid logout");
        }

        return ResponseEntity.ok().body("user logout successfully");
    }
}

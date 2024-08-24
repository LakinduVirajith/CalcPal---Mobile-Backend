package com.calcpal.userservice.service;

import com.calcpal.userservice.collection.ActivationToken;
import com.calcpal.userservice.collection.AuthenticationToken;
import com.calcpal.userservice.collection.PasswordResetOTP;
import com.calcpal.userservice.collection.User;
import com.calcpal.userservice.common.AuthenticationRequest;
import com.calcpal.userservice.config.jwt.JwtService;
import com.calcpal.userservice.dto.FullUserDTO;
import com.calcpal.userservice.dto.UserDTO;
import com.calcpal.userservice.common.AuthenticationResponse;
import com.calcpal.userservice.common.CommonFunctions;
import com.calcpal.userservice.dto.UserUpdateDTO;
import com.calcpal.userservice.enums.DisorderType;
import com.calcpal.userservice.exception.NotFoundException;
import com.calcpal.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

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
    public ResponseEntity<?> userSignUp(UserDTO userDTO) {
        Optional<User> emailCondition = userRepository.findByEmail(userDTO.getEmail());
        if(emailCondition.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use. Please log in or use a different email.");
        }

        // ASSIGN USER DATA TO THE USER OBJECT
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .age(calculateAge(userDTO.getBirthday()))
                .birthday(userDTO.getBirthday())
                .isActive(false)
                .build();

        // ENCODE PASSWORD
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);

        // CALLING EMAIL SERVICE
        boolean status = sendActivationMail(user);

        if(status){
            userRepository.save(user);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email service error orchard. try again");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful! Please verify your email before logging in.");
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found");
        }
        User user = optionalUser.get();

        // CHECK ACTIVATION TOKEN EXPIRY
        LocalDateTime activationTokenExpiry = user.getActivationToken().getTokenExpiry();
        if (activationTokenExpiry.isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Activation token has expired");
        }

        user.setIsActive(true);
        userRepository.save(user);

        return ResponseEntity.ok().body("User activated successfully");
    }

    @Override
    public ResponseEntity<?> login(AuthenticationRequest request) {
        Optional<User> userCondition = userRepository.findByEmail(request.getEmail());

        // CHECK ACCOUNT STATUS
        if(userCondition.isPresent() && !userCondition.get().getIsActive()){
            boolean status = sendActivationMail(userCondition.get());
            if(status){
                userRepository.save(userCondition.get());
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email service error orchard. try again");
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account is not activated. Please check your email to verify your account first.");
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
                .message("User authenticated successfully")
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build()
        );
    }

    // METHOD TO GENERATE ACTIVATION TOKEN AND SEND ACTIVATION MAIL
    private boolean sendActivationMail(User user) {
        // GENERATE ACTIVATION TOKEN
        String token = UUID.randomUUID().toString().substring(0, 32);

        // ACTIVATION LINK VALID FOR 10 MINTS
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
    public ResponseEntity<?> getUser() throws NotFoundException {
        User user = commonFunctions.getUser();
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found");
        }

        // ASSIGN USER DETAILS
        FullUserDTO fullUserDTO = FullUserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .birthDay(user.getBirthday())
                .build();
        if(user.getDisorderTypes() != null){
            fullUserDTO.setDisorderTypes(user.getDisorderTypes());
        }
        if(user.getIqScore() != null){
            fullUserDTO.setIqScore(user.getIqScore());
        }

        return ResponseEntity.ok().body(fullUserDTO);
    }

    @Override
    public ResponseEntity<?> updateDetails(UserUpdateDTO userUpdateDTO) throws NotFoundException {
        User user = commonFunctions.getUser();
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found");
        }

        if(!userUpdateDTO.getName().isEmpty()){
            user.setName(userUpdateDTO.getName());
        }
        if(!userUpdateDTO.getBirthDay().isEmpty()){
            user.setBirthday(userUpdateDTO.getBirthDay());
            user.setAge(calculateAge(userUpdateDTO.getBirthDay()));
        }

        userRepository.save(user);

        return ResponseEntity.ok().body("User details updated successfully");
    }

    @Override
    public ResponseEntity<?> updateIQScore(String iqScore) throws NotFoundException {
        User user = commonFunctions.getUser();
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found");
        }

        if(!iqScore.isEmpty()){
            user.setIqScore(iqScore);
        }

        userRepository.save(user);

        return ResponseEntity.ok().body("User IQ score updated successfully");
    }

    @Override
    public ResponseEntity<?> updateDisorderTypes(String disorderType) throws NotFoundException {
        User user = commonFunctions.getUser();
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found");
        }

        if (disorderType != null && !disorderType.isEmpty()) {
            // VALIDATE THE DISORDER TYPE AGAINST ENUM VALUES
            DisorderType validatedDisorderType = null;
            for (DisorderType type : DisorderType.values()) {
                if (type.name().equals(disorderType)) {
                    validatedDisorderType = type;
                    break;
                }
            }

            if (validatedDisorderType == null) {
                // INVALID DISORDER TYPE
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid disorder type");
            }

            // INITIALIZE THE USER'S EXISTING DISORDER TYPES LIST IF IT'S NULL
            if (user.getDisorderTypes() == null) {
                user.setDisorderTypes(new ArrayList<>());
            }

            // ADD THE DISORDER TYPE TO THE LIST ONLY IF IT DOESN'T ALREADY EXIST
            if (!user.getDisorderTypes().contains(disorderType)) {
                user.getDisorderTypes().add(disorderType);
            }
        }

        userRepository.save(user);
        return ResponseEntity.ok().body("Disorder types updated successfully");
    }

    @Override
    public ResponseEntity<?> resetPasswordOTP(String email) {
        Optional<User> userCondition = userRepository.findByEmail(email);

        // CHECK IF USER EXISTS
        if(userCondition.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid user email account");
        }
        User user = userCondition.get();

        // CALL EMAIL SERVICE TO SEND RESET PASSWORD OTP
        boolean status = sendResetPasswordMail(user);

        if(status){
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Password reset email sent successfully");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email service error. Please try again.");
        }
    }

    // METHOD TO GENERATE RESET PASSWORD CODE AND SEND ACTIVATION MAIL
    private boolean sendResetPasswordMail(User user) {
        // GENERATE PASSWORD RESET OTP
        SecureRandom secureRandom = new SecureRandom();
        int OTP = secureRandom.nextInt(9000) + 1000;

        // OTP VALID FOR 4 MINTS
        LocalDateTime OTPExpiry = LocalDateTime.now().plusMinutes(4);
        PasswordResetOTP passwordResetOTP = PasswordResetOTP.builder()
                .OTP(OTP)
                .OTPExpiry(OTPExpiry)
                .build();

        user.setPasswordResetOTP(passwordResetOTP);

        // CALLING EMAIL SERVICE
        return emailService.sendResetPasswordMail(user);
    }

    @Override
    public ResponseEntity<?> resetPasswordValidation(String email, int otp) {
        Optional<User> userCondition = userRepository.findByEmail(email);

        // CHECK IF USER EXISTS
        if(userCondition.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid user email account");
        }
        User user = userCondition.get();

        // CHECK OTP EXPIRY
        LocalDateTime OTPExpiry = user.getPasswordResetOTP().getOTPExpiry();
        if (OTPExpiry.isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.GONE).body("OTP has expired. Please request a new one.");
        }

        // CHECK OTP VALIDITY
        if(otp != user.getPasswordResetOTP().getOTP()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP. Please try again.");
        }

        return ResponseEntity.ok().body("User OTP validated successfully");
    }

    @Override
    public ResponseEntity<?> resetPassword(AuthenticationRequest request) {
        Optional<User> userCondition = userRepository.findByEmail(request.getEmail());

        // INVALID USER EXCEPTION
        if(userCondition.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid user email account");
        }
        User user = userCondition.get();

        // ENCODE THE NEW PASSWORD
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // UPDATE USER PASSWORD
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return ResponseEntity.ok().body("Password reset successfully");
    }

    @Override
    public ResponseEntity<?> refreshToken(String refreshToken) {
        final String userEmail;
        userEmail = jwtService.extractUsername(refreshToken);

        // INVALID TOKEN EXCEPTION
        if(userEmail == null || userEmail.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The provided refresh token is invalid or expired.");
        }

        // INVALID ACCOUNT EXCEPTION
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent() && !optionalUser.get().getIsActive()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account is not activated. Please activate your account before requesting a new token.");
        }

        // INVALID USER EXCEPTION
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found for the provided email address.");
        }
       User user = optionalUser.get();

        // GENERATE TOKEN
        if (jwtService.isTokenValid(refreshToken, user)) {
            String newAccessToken = jwtService.generateToken(user);
            saveToken(user, newAccessToken);

            String newRefreshToken = jwtService.generateRefreshToken(user);

            AuthenticationResponse authResponse = AuthenticationResponse.builder()
                    .message("Authentication was successful using the refresh token.")
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();

            return ResponseEntity.ok().body(authResponse);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The provided refresh token is invalid or expired. Please request a new refresh token.");
        }
    }

    @Override
    public ResponseEntity<?> deactivate() throws NotFoundException {
        User user = commonFunctions.getUser();
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found");
        }

        user.setIsActive(false);
        userRepository.save(user);

        return ResponseEntity.ok().body("User account deactivated successfully");
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid logout request");
        }

        return ResponseEntity.ok().body("Logout successful. You have been logged out.");
    }
}

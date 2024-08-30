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
import org.springframework.web.server.ResponseStatusException;

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending activation email. Please try again later.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Sign-up successful! Please check your email to verify your account before logging in.");
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

        // ASSIGN USER DATA
        User user = userCondition.orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "No user found with the provided email address."
        ));

        // CHECK ACCOUNT STATUS
        if(!user.getIsActive()){
            boolean status = sendActivationMail(user);
            if(status){
                userRepository.save(user);
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending activation email. Please try again later.");
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account is not activated. Please check your email and verify your account.");
        }

        // VERIFY THE PASSWORD
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password. Please check and try again.");
        }

        // SPRING AUTHENTICATION MANAGER
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        

        // GENERATE AND SAVE ACCESS TOKEN
        String jwtToken = jwtService.generateToken(user);
        saveToken(user, jwtToken);

        // GENERATE REFRESH TOKEN
        String refreshToken = jwtService.generateRefreshToken(user);

        return ResponseEntity.ok().body(AuthenticationResponse.builder()
                .message("Login successful! Welcome back.")
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found. Please check the provided information.");
        }

        // ASSIGN USER DETAILS
        FullUserDTO fullUserDTO = FullUserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .birthday(user.getBirthday())
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found. Please make sure you are logged in.");
        }

        if(!userUpdateDTO.getName().isEmpty()){
            user.setName(userUpdateDTO.getName());
        }
        if(!userUpdateDTO.getBirthday().isEmpty()){
            user.setBirthday(userUpdateDTO.getBirthday());
            user.setAge(calculateAge(userUpdateDTO.getBirthday()));
        }

        userRepository.save(user);

        return ResponseEntity.ok().body("Your details have been updated successfully.");
    }

    @Override
    public ResponseEntity<?> updateIQScore(Integer iqScore) throws NotFoundException {
        User user = commonFunctions.getUser();
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found");
        }

        if(iqScore != null){
            user.setIqScore(iqScore);
        }

        userRepository.save(user);

        return ResponseEntity.ok().body("User IQ score updated successfully");
    }

    @Override
    public ResponseEntity<?> updateDisorderTypes(String disorderType) throws NotFoundException {
        // RETRIEVE THE CURRENT USER
        User user = commonFunctions.getUser();

        // CHECK IF THE USER IS NULL AND HANDLE IT
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found. Please log in and try again.");
        }

        // INITIALIZE THE USER'S EXISTING DISORDER TYPES LIST IF IT'S NULL
        if (user.getDisorderTypes() == null) {
            user.setDisorderTypes(new ArrayList<>());
        }

        // CHECK IF THE DISORDER TYPE INDICATES REMOVAL (E.G., "noVerbal")
        if (disorderType.contains("no")) {
            String disorder = disorderType.replaceFirst("no", "");
        
            // REMOVE THE DISORDER TYPE IF IT EXISTS IN THE LIST
            boolean remove = user.getDisorderTypes().remove(disorder.toLowerCase());
            if (remove) {
                userRepository.save(user);
                return ResponseEntity.ok().body("Disorder type has been removed successfully.");
            } else {
                return ResponseEntity.ok().body("The disorder type to remove does not exist.");
            }
        }

        // ATTEMPT TO VALIDATE THE DISORDER TYPE AGAINST ENUM VALUES
        DisorderType validatedDisorderType = null;
        for (DisorderType type : DisorderType.values()) {
            if (type.name().equalsIgnoreCase(disorderType)) {
                validatedDisorderType = type;
                break;
            }
        }
        
        // IF THE DISORDER TYPE IS INVALID, RETURN A BAD REQUEST RESPONSE
        if (validatedDisorderType == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The disorder type provided is not valid.");
        }

        // ADD THE DISORDER TYPE TO THE LIST ONLY IF IT DOESN'T ALREADY EXIST
        if (!user.getDisorderTypes().contains(disorderType)) {
            user.getDisorderTypes().add(disorderType.toLowerCase());
            userRepository.save(user);
            return ResponseEntity.ok().body("Disorder type has been added successfully.");
        }else{
            return ResponseEntity.ok().body("The disorder type is already exist.");   
        }
    }

    @Override
    public ResponseEntity<?> resetPasswordOTP(String email) {
        Optional<User> userCondition = userRepository.findByEmail(email);

        // CHECK IF USER EXISTS
        if(userCondition.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No account found with the provided email.");
        }
        User user = userCondition.get();

        // CALL EMAIL SERVICE TO SEND RESET PASSWORD OTP
        boolean status = sendResetPasswordMail(user);

        if(status){
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("A password reset email has been sent. Please check your inbox for further instructions.");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while sending the reset email. Please try again.");
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No account found with the provided email.");
        }
        User user = userCondition.get();

        // CHECK OTP EXPIRY
        LocalDateTime OTPExpiry = user.getPasswordResetOTP().getOTPExpiry();
        if (OTPExpiry.isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.GONE).body("The OTP has expired. Please request a new one.");
        }

        // CHECK OTP VALIDITY
        if(otp != user.getPasswordResetOTP().getOTP()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The OTP provided is incorrect. Please try again.");
        }

        return ResponseEntity.ok().body("OTP validated successfully.");
    }

    @Override
    public ResponseEntity<?> resetPassword(AuthenticationRequest request) {
        Optional<User> userCondition = userRepository.findByEmail(request.getEmail());

        // INVALID USER EXCEPTION
        if(userCondition.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No account found with the provided email.");
        }
        User user = userCondition.get();

        // ENCODE THE NEW PASSWORD
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // UPDATE USER PASSWORD
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return ResponseEntity.ok().body("Your password has been reset successfully.");
    }

    @Override
    public ResponseEntity<?> refreshToken(String refreshToken) {
        final String userEmail;
        userEmail = jwtService.extractUsername(refreshToken);

        // INVALID TOKEN EXCEPTION
        if(userEmail == null || userEmail.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token is invalid or expired. Please log in again.");
        }

        // INVALID ACCOUNT EXCEPTION
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent() && !optionalUser.get().getIsActive()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account is not activated. Please activate your account to proceed.");
        }

        // INVALID USER EXCEPTION
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found. Please check your email or register a new account.");
        }
       User user = optionalUser.get();

        // GENERATE TOKEN
        if (jwtService.isTokenValid(refreshToken, user)) {
            String newAccessToken = jwtService.generateToken(user);
            saveToken(user, newAccessToken);

            String newRefreshToken = jwtService.generateRefreshToken(user);

            AuthenticationResponse authResponse = AuthenticationResponse.builder()
                    .message("Authentication successful. New tokens have been issued.")
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Refresh token is invalid or expired. Please log in again.");
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

        return ResponseEntity.ok().body("You've been successfully logged out. See you next time!");
    }
}

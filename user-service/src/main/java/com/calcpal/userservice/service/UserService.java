package com.calcpal.userservice.service;

import com.calcpal.userservice.common.AuthenticationRequest;
import com.calcpal.userservice.dto.UserDTO;
import com.calcpal.userservice.dto.UserUpdateDTO;
import com.calcpal.userservice.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> userSignUp(UserDTO userDTO);

    ResponseEntity<?> activate(String token);

    ResponseEntity<?> login(AuthenticationRequest request);

    ResponseEntity<?> getUser() throws NotFoundException;

    ResponseEntity<?> updateDetails(UserUpdateDTO userUpdateDTO) throws NotFoundException;

    ResponseEntity<?> updateIQScore(String iqScore) throws NotFoundException;

    ResponseEntity<?> updateDisorderTypes(String disorderType) throws NotFoundException;

    ResponseEntity<?> resetPasswordOTP(String email);

    ResponseEntity<?> resetPasswordValidation(String email, int otp);

    ResponseEntity<?> resetPassword(AuthenticationRequest request);

    ResponseEntity<?> refreshToken(String refreshToken);

    ResponseEntity<?> deactivate() throws NotFoundException;

    ResponseEntity<?> logout() throws NotFoundException;
}

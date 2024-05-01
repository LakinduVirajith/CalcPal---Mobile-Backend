package com.calcpal.userservice.service;

import com.calcpal.userservice.common.AuthenticationRequest;
import com.calcpal.userservice.dto.UserDTO;
import com.calcpal.userservice.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> userRegister(UserDTO userDTO);

    ResponseEntity<?> activate(String token);

    ResponseEntity<?> login(AuthenticationRequest request);

    ResponseEntity<?> getUser(String email);

    ResponseEntity<?> updateDetails(String name, String birthDay) throws NotFoundException;

    ResponseEntity<?> updateDisorderTypes(String disorderTypes) throws NotFoundException;

    ResponseEntity<?> resetPassword(AuthenticationRequest request);

    ResponseEntity<?> refreshToken(String refreshToken);

    ResponseEntity<?> deactivate() throws NotFoundException;

    ResponseEntity<?> logout() throws NotFoundException;

}

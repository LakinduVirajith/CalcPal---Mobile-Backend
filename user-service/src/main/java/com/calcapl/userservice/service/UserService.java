package com.calcapl.userservice.service;

import com.calcapl.userservice.common.AuthenticationRequest;
import com.calcapl.userservice.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> userRegister(UserDTO userDTO);

    ResponseEntity<?> activate(String token);

    ResponseEntity<?> login(AuthenticationRequest request);

    ResponseEntity<?> getUser(String email);

    ResponseEntity<?> update(UserDTO userDTO);

    ResponseEntity<?> resetPassword(AuthenticationRequest request);

    ResponseEntity<?> refreshToken(String refreshToken);

    ResponseEntity<?> deactivate();

    ResponseEntity<?> logout();

}

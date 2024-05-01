package com.calcapl.userservice.controller;

import com.calcapl.userservice.common.AuthenticationRequest;
import com.calcapl.userservice.dto.UserDTO;
import com.calcapl.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Controllers")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "User Registration", description = "Register a new user. Provide necessary details to create a user account.")
    public ResponseEntity<?> userRegister(@Valid @RequestBody UserDTO userDTO) {
        return userService.userRegister(userDTO);
    }

    @GetMapping("/activate")
    @Operation(summary = "Activate User Account", description = "Activate a user account using an activation token received via email.")
    public ResponseEntity<?> userActivate(@RequestParam("token") String token) {
        return userService.activate(token);
    }

    @PostMapping("/login")
    @Operation(summary = "User Authentication", description = "Authenticate a user by providing valid credentials.")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        return userService.login(request);
    }

    @GetMapping("/details")
    @Operation(summary = "Retrieve user details", description = "Retrieve a user details by providing email of the user.")
    public ResponseEntity<?> getUser(@RequestBody String email) {
        return userService.getUser(email);
    }

    @PutMapping("/update")
    @Operation(summary = "Update user details", description = "Update a new user. Provide necessary details to create a user account.")
    public ResponseEntity<?> update(@Valid @RequestBody UserDTO userDTO) {
        return userService.update(userDTO);
    }

    @Operation(summary = "Reset Password", description = "User password reset by Providing necessary details.")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody AuthenticationRequest request) {
        return userService.resetPassword(request);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh Access Token", description = "Refresh the access token by providing a valid refresh token. This endpoint allows you to obtain a new access token using a valid refresh token, which helps in maintaining user authentication without requiring the user to log in again.")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
        return userService.refreshToken(refreshToken);
    }

    @PutMapping("/deactivate")
    @Operation(summary = "Deactivate Account", description = "Deactivate a user account by providing the unique user ID.")
    public ResponseEntity<?> userDeactivate() {
        return userService.deactivate();
    }

    @PutMapping("/logout")
    @Operation(summary = "Logout", description = "Invalidate the user's authentication token to log out.")
    public ResponseEntity<?> logout() {
        return userService.logout();
    }
}

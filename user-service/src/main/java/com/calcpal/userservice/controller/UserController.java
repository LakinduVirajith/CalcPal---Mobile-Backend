package com.calcpal.userservice.controller;

import com.calcpal.userservice.common.AuthenticationRequest;
import com.calcpal.userservice.dto.UserDTO;
import com.calcpal.userservice.dto.UserUpdateDTO;
import com.calcpal.userservice.exception.NotFoundException;
import com.calcpal.userservice.service.UserService;
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
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest request) {
        return userService.login(request);
    }

    @GetMapping("/details")
    @Operation(summary = "Retrieve User Details", description = "Retrieve user details by providing email.")
    public ResponseEntity<?> getUser() throws NotFoundException {
        return userService.getUser();
    }

    @PutMapping("/update")
    @Operation(summary = "Update User Details", description = "Update user details such as name and birthday.")
    public ResponseEntity<?> updateDetails(@Valid @RequestBody UserUpdateDTO userUpdateDTO) throws NotFoundException {
        return userService.updateDetails(userUpdateDTO);
    }

    @PutMapping("/update/iq")
    @Operation(summary = "Update User IQ Score", description = "Update user IQ Score.")
    public ResponseEntity<?> updateIQScore(@RequestBody String iqScore) throws NotFoundException {
        return userService.updateIQScore(iqScore);
    }

    @PutMapping("/update/disorder")
    @Operation(summary = "Update User Disorder Type", description = "Update user disorder types.")
    public ResponseEntity<?> updateDisorderTypes(@RequestBody String disorderTypes) throws NotFoundException {
        return userService.updateDisorderTypes(disorderTypes);
    }

    @Operation(summary = "Reset Password", description = "User password reset by Providing necessary details.")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody AuthenticationRequest request) {
        return userService.resetPassword(request);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh Access Token", description = "Refresh access token using a valid refresh token.")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
        return userService.refreshToken(refreshToken);
    }

    @PutMapping("/deactivate")
    @Operation(summary = "Deactivate Account", description = "Deactivate user account.")
    public ResponseEntity<?> userDeactivate() throws NotFoundException {
        return userService.deactivate();
    }

    @PutMapping("/logout")
    @Operation(summary = "Logout", description = "Invalidate the user's authentication token to log out.")
    public ResponseEntity<?> logout() throws NotFoundException {
        return userService.logout();
    }
}

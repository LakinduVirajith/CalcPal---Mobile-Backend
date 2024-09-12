package com.calcpal.userservice.common;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @NotNull
    private String message;

    @NotNull
    private String accessToken;

    @NotNull
    private String refreshToken;
}

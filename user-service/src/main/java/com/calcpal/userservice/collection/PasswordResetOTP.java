package com.calcpal.userservice.collection;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetOTP {

    @NotNull
    private int OTP;

    @NotNull
    private LocalDateTime OTPExpiry;
}

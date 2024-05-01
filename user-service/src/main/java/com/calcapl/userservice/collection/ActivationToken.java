package com.calcapl.userservice.collection;

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
public class ActivationToken {

    @NotNull
    private String token;

    @NotNull
    private LocalDateTime tokenExpiry;
}

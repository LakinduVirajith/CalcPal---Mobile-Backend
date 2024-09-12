package com.calcpal.userservice.collection;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationToken {

    @NotNull
    private String accessToken;

    @Builder.Default
    private boolean expired = false;

    @Builder.Default
    private boolean revoked = false;
}

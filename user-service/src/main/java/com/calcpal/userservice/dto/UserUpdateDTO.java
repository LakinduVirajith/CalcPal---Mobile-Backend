package com.calcpal.userservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    @NotNull
    private String name;

    @NotNull
    private String birthday;
}

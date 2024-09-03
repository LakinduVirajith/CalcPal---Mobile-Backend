package com.calcpal.userservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullUserDTO {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private Integer age;

    @NotNull
    private String birthday;

    private List<String> disorderTypes;

    private Integer iqScore;
}

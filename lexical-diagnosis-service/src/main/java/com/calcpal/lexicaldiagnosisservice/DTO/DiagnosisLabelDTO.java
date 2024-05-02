package com.calcpal.lexicaldiagnosisservice.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisLabelDTO {

    @NotNull
    private String userEmail;

    @NotNull
    private Boolean label;
}

package com.calcpal.operationaldiagnosisservice.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DiagnosisDTO {

    @NotNull
    private String userEmail;

    @NotNull
    private Boolean label;
}
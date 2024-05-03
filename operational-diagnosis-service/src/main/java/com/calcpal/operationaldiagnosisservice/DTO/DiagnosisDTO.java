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

    public DiagnosisDTO() {
    }

    public DiagnosisDTO(String userEmail, Boolean label) {
        this.userEmail = userEmail;
        this.label = label;
    }

}
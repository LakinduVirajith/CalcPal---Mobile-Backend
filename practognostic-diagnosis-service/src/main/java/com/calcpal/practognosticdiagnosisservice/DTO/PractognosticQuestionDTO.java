package com.calcpal.practognosticdiagnosisservice.DTO;

import com.calcpal.practognosticdiagnosisservice.enums.Languages;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PractognosticQuestionDTO {
    @NotNull
    private Long questionNumber;

    @NotNull
    private Languages language;

    @NotNull
    private String question;

    @NotNull
    private String answer;
}

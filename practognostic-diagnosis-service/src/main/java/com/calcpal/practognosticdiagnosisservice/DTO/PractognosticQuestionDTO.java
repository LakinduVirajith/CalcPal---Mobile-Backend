package com.calcpal.practognosticdiagnosisservice.DTO;

import com.calcpal.practognosticdiagnosisservice.enums.Languages;
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
public class PractognosticQuestionDTO {
    @NotNull
    private Long questionNumber;

    @NotNull
    private Languages language;

    @NotNull
    private String question;

    private String questionText;

    private String imageType;

    @NotNull
    private List<String> answers;

    @NotNull
    private String correctAnswer;
}

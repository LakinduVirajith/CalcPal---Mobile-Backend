package com.calcpal.operationaldiagnosisservice.DTO;

import com.calcpal.operationaldiagnosisservice.enums.Language;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QuestionDTO {

    @NotNull
    private Long questionNumber;

    @NotNull
    private Language language;

    @NotNull
    private String question;

    @NotNull
    private String correctAnswer;

    @NotNull
    private String incorrectAnswer1;

    @NotNull
    private String incorrectAnswer2;
}

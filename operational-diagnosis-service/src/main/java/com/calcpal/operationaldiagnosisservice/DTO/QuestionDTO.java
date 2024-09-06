package com.calcpal.operationaldiagnosisservice.DTO;

import java.util.List;

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
    private Integer correctAnswer;

    @NotNull
    private List<Integer> allAnswers;
}

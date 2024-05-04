package com.calcpal.operationaldiagnosisservice.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Language;

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

    public QuestionDTO() {
    }

    public QuestionDTO(Long questionNumber, Language language, String question, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2) {
        this.questionNumber = questionNumber;
        this.language = language;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswer1 = incorrectAnswer1;
        this.incorrectAnswer2 = incorrectAnswer2;
    }
}

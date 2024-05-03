package com.calcpal.operationaldiagnosisservice.collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Language;

@Document("Question-Bank")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class operationalDiagnosisQues {

    @Id
    private String id;

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

    public operationalDiagnosisQues() {
    }

    public operationalDiagnosisQues(String id, Long questionNumber, Language language, String question, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2) {
        this.id = id;
        this.questionNumber = questionNumber;
        this.language = language;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswer1 = incorrectAnswer1;
        this.incorrectAnswer2 = incorrectAnswer2;
    }
}

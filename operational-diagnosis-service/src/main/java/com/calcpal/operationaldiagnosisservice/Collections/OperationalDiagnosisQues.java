package com.calcpal.operationaldiagnosisservice.Collections;

import com.calcpal.operationaldiagnosisservice.enums.Language;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Question-Bank")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class OperationalDiagnosisQues {

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
}

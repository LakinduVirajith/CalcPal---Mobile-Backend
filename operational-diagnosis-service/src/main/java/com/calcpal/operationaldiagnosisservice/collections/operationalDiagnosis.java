package com.calcpal.operationaldiagnosisservice.collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Diagnosis-Result")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class operationalDiagnosis {

    @Id
    private String userEmail;

    @NotNull
    private Long quizTimeTaken;

    @NotNull
    private Boolean q1;

    @NotNull
    private Boolean q2;

    @NotNull
    private Boolean q3;

    @NotNull
    private Boolean q4;

    @NotNull
    private Boolean q5;

    @NotNull
    private String score;

    private Boolean diagnosis;

    public operationalDiagnosis() {
    }

    public operationalDiagnosis(String userEmail, Long quizTimeTaken, Boolean q1, Boolean q2, Boolean q3, Boolean q4, Boolean q5, String score) {
        this.userEmail = userEmail;
        this.quizTimeTaken = quizTimeTaken;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.score = score;
    }

    public operationalDiagnosis(String userEmail, Long quizTimeTaken, Boolean q1, Boolean q2, Boolean q3, Boolean q4, Boolean q5, String score, Boolean diagnosis) {
        this.userEmail = userEmail;
        this.quizTimeTaken = quizTimeTaken;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.score = score;
        this.diagnosis = diagnosis;
    }
}

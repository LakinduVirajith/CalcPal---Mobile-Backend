package com.calcpal.operationaldiagnosisservice.Collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Diagnosis-Result")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}

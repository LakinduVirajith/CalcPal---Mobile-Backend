package com.calcpal.practognosticdiagnosisservice.collection;


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
public class DiagnosisResultPractognostic {
    @Id
    private String userEmail;

    @NotNull
    private Long timeSeconds;

    @NotNull
    private String q1;

    @NotNull
    private String q2;

    @NotNull
    private String q3;

    @NotNull
    private String q4;

    @NotNull
    private String q5;

    @NotNull
    private String totalScore;

    private Boolean label;

}

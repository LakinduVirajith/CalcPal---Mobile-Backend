package com.calcpal.operationaldiagnosisservice.Collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("OperationalActivities")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class operationalActivities {

    @Id
    private String userEmail;

    @NotNull
    private Integer level;
    private String date;
    private Activity addition;
    private Activity subtraction;
    private Activity multiplication;
    private Activity division;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Activity {
        private List<Exercise> exercises;
        private Long timeTaken;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Exercise {
        private Integer exerciseNo;
        private Boolean isCorrect;
        private Integer retries;
    }
}


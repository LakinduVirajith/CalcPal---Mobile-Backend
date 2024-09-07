package com.calcpal.operationaldiagnosisservice.DTO;

import com.calcpal.operationaldiagnosisservice.Collections.operationalActivities;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
public class ActivityDTO {
    @NotNull
    private String userEmail;

    @NotNull
    private Integer level;
    private String date;

    private operationalActivities.Activity addition;
    private operationalActivities.Activity subtraction;
    private operationalActivities.Activity multiplication;
    private operationalActivities.Activity division;

    @Data
    @Builder
    public static class Activity {
        private List<operationalActivities.Exercise> exercises;
        private Long timeTaken;
    }

    @Data
    @Builder
    public static class Exercise {
        private Integer exerciseNo;
        private Boolean isCorrect;
        private Integer retries;
    }
}

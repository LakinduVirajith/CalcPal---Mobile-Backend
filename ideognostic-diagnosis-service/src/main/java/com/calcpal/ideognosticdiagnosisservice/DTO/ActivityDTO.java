package com.calcpal.ideognosticdiagnosisservice.DTO;

import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticActivities;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {
    @NotNull
    private String userEmail;

    @NotNull
    private Integer level;
    private String date;

    private IdeognosticActivities.Activity numberLine;
    private IdeognosticActivities.Activity fraction;
    private IdeognosticActivities.Activity numberCreation;


    @Data
    @Builder
    public static class Activity {
        private String name;
        private List<IdeognosticActivities.Exercise> exercises;
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
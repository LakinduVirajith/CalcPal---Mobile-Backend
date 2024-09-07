package com.calcpal.ideognosticdiagnosisservice.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("IdeognosticActivities")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdeognosticActivities {
    @Id
    private String userEmail;

    private Integer level;
    private String date;
    private Activity numberLine;
    private Activity fraction;
    private Activity numberCreation;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Activity {
        private String name;
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

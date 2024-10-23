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
    private String id;

    private String userEmail;

    private String date;

    private String activityName;

    private Long timeTaken;

    private Long totalScore;

    private Long retries;

}

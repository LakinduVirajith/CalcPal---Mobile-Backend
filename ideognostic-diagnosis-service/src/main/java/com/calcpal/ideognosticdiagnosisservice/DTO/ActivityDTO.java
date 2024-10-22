package com.calcpal.ideognosticdiagnosisservice.DTO;

import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticActivities;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {

    private String userEmail;

    private String date;

    private String activityName;

    private Long timeTaken;

    private Long totalScore;

    private Long retries;
}
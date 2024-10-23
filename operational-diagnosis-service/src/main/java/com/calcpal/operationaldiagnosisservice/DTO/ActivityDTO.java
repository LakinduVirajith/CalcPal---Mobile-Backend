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
    private String userEmail;

    private String date;

    private String activityName;

    private Long timeTaken;

    private Long totalScore;

    private Long retries;
}

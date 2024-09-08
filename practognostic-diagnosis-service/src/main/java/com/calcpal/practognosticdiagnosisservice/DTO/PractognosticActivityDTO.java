package com.calcpal.practognosticdiagnosisservice.DTO;

import com.calcpal.practognosticdiagnosisservice.enums.Languages;
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
public class PractognosticActivityDTO {

    @NotNull
    private Long activityNumber;

    @NotNull
    private Languages language;

    @NotNull
    private String activityLevelType;

    @NotNull
    private String question;

    private String questionText;

    private String imageType;

    private List<String> answers;

    private String correctAnswer;

}

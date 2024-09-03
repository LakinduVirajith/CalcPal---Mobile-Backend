package com.calcpal.verbaldiagnosisservice.DTO;

import com.calcpal.verbaldiagnosisservice.enums.Language;
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
public class VerbalActivityDTO {

    @NotNull
    private Long activityNumber;

    @NotNull
    private Language language;

    @NotNull
    private String question;

    @NotNull
    private String answer;

    @NotNull
    private List<String> answers;

    @NotNull
    private String correctAnswerAudioText;

    @NotNull
    private String wrongAnswerAudioText;
}

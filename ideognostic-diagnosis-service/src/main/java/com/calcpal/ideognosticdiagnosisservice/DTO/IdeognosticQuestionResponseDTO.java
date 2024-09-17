package com.calcpal.ideognosticdiagnosisservice.DTO;

import jakarta.validation.constraints.NotNull;
import com.calcpal.ideognosticdiagnosisservice.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdeognosticQuestionResponseDTO {
    @NotNull
    private Long questionNumber;

    @NotNull
    private Language language;

    @NotNull
    private String question;

    @NotNull
    private String correctAnswer;

    private List<String> allAnswers;

    private String image;  // Base64 encoded image string for response
}
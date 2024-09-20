package com.calcpal.ideognosticdiagnosisservice.DTO;

import com.calcpal.ideognosticdiagnosisservice.enums.Language;
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
public class IdeognosticQuestionUploadDTO {

    @NotNull
    private Long questionNumber;

    @NotNull
    private Language language;

    @NotNull
    private String question;

    @NotNull
    private String correctAnswer;

    private List<String> allAnswers;

    private String base64image; // Store image as byte array

}

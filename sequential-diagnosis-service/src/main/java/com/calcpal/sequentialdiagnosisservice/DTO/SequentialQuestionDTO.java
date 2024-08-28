package com.calcpal.sequentialdiagnosisservice.DTO;

import com.calcpal.sequentialdiagnosisservice.enums.Language;
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
public class SequentialQuestionDTO {

    @NotNull
    private Long questionNumber;

    @NotNull
    private Language language;

    @NotNull
    private String question;

    @NotNull
    private List<String> answers;

    @NotNull
    private String correctAnswer;

}

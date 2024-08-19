package com.calcpal.lexicaldiagnosisservice.DTO;

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
public class LexicalQuestionDTO {

    @NotNull
    private Long questionNumber;

    @NotNull
    private String question;

    @NotNull
    private List<String> answers;
}

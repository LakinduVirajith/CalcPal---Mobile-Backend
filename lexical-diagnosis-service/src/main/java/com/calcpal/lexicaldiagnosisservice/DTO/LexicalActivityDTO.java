package com.calcpal.lexicaldiagnosisservice.DTO;

import com.calcpal.lexicaldiagnosisservice.enums.Language;
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
public class LexicalActivityDTO {

    @NotNull
    private Long activityNumber;

    @NotNull
    private Language language;

    @NotNull
    private String question;

    private List<String> answers;

    @NotNull
    private String correctAnswer;

}

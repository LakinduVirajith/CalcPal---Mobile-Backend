package com.calcpal.operationaldiagnosisservice.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Language;

@Builder
@Data
public class QuestionDTO {

    @NotNull
    private Long questionNumber;

    @NotNull
    private Language language;

    @NotNull
    private String question;

    @NotNull
    private String answers;
}

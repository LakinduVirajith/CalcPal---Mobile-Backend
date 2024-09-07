package com.calcpal.practognosticdiagnosisservice.collection;
import com.calcpal.practognosticdiagnosisservice.enums.Languages;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Question_Bank")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisQuestionPractognostic {
    @Id
    private String ID;

    @NotNull
    private Long questionNumber;

    @NotNull
    private Languages language;

    @NotNull
    private String question;

    private String questionText;

    private String imageType;

    @NotNull
    private List<String> answers;

    @NotNull
    private String correctAnswer;

}

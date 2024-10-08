package com.calcpal.ideognosticdiagnosisservice.collection;

import com.calcpal.ideognosticdiagnosisservice.enums.Language;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Question-Bank")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdeognosticQuestion {

    @Id
    private String id;

    @NotNull
    private Long questionNumber;

    @NotNull
    private Language language;

    @NotNull
    private String question;

    @NotNull
    private String correctAnswer;

//    private MultipartFile image;
}

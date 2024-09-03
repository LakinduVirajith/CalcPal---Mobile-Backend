package com.calcpal.lexicaldiagnosisservice.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Question-Bank")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LexicalQuestion {

    @Id
    private String id;

    @NotNull
    private Long questionNumber;

    @NotNull
    private String question;

    @NotNull
    private List<String> answers;
}

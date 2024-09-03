package com.calcpal.lexicaldiagnosisservice.collection;

import com.calcpal.lexicaldiagnosisservice.enums.Language;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Activity-Bank")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LexicalActivity {

    @Id
    private String id;

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

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

@Document("Activity-Bank")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PractognosticActivity {

    @Id
    private String id;

    @NotNull
    private Long activityNumber;

    @NotNull
    private Languages language;

    @NotNull
    private String activityLevelType;

    @NotNull
    private String question;

    private String questionText;

    private String imageType;

    private List<String> answers;

    private String correctAnswer;
}

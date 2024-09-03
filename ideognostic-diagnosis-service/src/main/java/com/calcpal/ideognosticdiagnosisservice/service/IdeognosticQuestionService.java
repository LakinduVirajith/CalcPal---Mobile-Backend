package com.calcpal.ideognosticdiagnosisservice.service;

import com.calcpal.ideognosticdiagnosisservice.DTO.IdeognosticQuestionDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface IdeognosticQuestionService {
    ResponseEntity<?> add(@Valid IdeognosticQuestionDTO questionDTO);

    ResponseEntity<?> getRandom(Long id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(String id, @Valid IdeognosticQuestionDTO questionDTO);

    ResponseEntity<?> delete(String id);
}

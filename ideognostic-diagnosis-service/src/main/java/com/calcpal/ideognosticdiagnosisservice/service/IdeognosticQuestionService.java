package com.calcpal.ideognosticdiagnosisservice.service;

import com.calcpal.ideognosticdiagnosisservice.DTO.IdeognosticQuestionUploadDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface IdeognosticQuestionService {
    ResponseEntity<?> add(@Valid IdeognosticQuestionUploadDTO questionDTO);

    ResponseEntity<?> getRandom(Long id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(String id, @Valid IdeognosticQuestionUploadDTO questionDTO);

    ResponseEntity<?> delete(String id);
}

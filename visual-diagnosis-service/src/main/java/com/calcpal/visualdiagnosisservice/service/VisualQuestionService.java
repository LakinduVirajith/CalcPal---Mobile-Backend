package com.calcpal.visualdiagnosisservice.service;

import com.calcpal.visualdiagnosisservice.DTO.VisualQuestionDTO;
import org.springframework.http.ResponseEntity;

public interface VisualQuestionService {
    ResponseEntity<?> add(VisualQuestionDTO questionDTO);

    ResponseEntity<?> getRandom(Long id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(String id, VisualQuestionDTO questionDTO);

    ResponseEntity<?> delete(String id);
}

package com.calcpal.graphicaldiagnosisservice.service;

import com.calcpal.graphicaldiagnosisservice.DTO.GraphicalQuestionDTO;
import org.springframework.http.ResponseEntity;

public interface GraphicalQuestionService {
    ResponseEntity<?> add(GraphicalQuestionDTO questionDTO);

    ResponseEntity<?> getRandom(Long id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(String id, GraphicalQuestionDTO questionDTO);

    ResponseEntity<?> delete(String id);
}

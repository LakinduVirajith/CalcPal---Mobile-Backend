package com.calcpal.sequentialdiagnosisservice.service;

import com.calcpal.sequentialdiagnosisservice.DTO.SequentialQuestionDTO;
import org.springframework.http.ResponseEntity;

public interface SequentialQuestionService {
    ResponseEntity<?> add(SequentialQuestionDTO questionDTO);

    ResponseEntity<?> getRandom(Long id, String language);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(String id, SequentialQuestionDTO questionDTO);

    ResponseEntity<?> delete(String id);
}

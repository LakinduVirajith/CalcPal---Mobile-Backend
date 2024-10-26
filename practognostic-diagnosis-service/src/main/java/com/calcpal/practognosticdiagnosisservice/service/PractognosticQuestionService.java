package com.calcpal.practognosticdiagnosisservice.service;

import com.calcpal.practognosticdiagnosisservice.DTO.PractognosticQuestionDTO;
import org.springframework.http.ResponseEntity;

public interface PractognosticQuestionService {
    ResponseEntity<?> add(PractognosticQuestionDTO questionDTO);

    ResponseEntity<?> getRandom(Long id, String language);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(String id, PractognosticQuestionDTO questionDTO);

    ResponseEntity<?> delete(String id);

}

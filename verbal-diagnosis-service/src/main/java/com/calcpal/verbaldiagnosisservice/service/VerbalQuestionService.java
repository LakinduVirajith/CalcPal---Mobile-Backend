package com.calcpal.verbaldiagnosisservice.service;

import com.calcpal.verbaldiagnosisservice.DTO.VerbalQuestionDTO;
import org.springframework.http.ResponseEntity;

public interface VerbalQuestionService {
    ResponseEntity<?> add(VerbalQuestionDTO questionBankDTO);

    ResponseEntity<?> getRandom(Long id, String language);

    ResponseEntity<?> getAll(int page, int size);

    ResponseEntity<?> update(String id, VerbalQuestionDTO questionBankDTO);

    ResponseEntity<?> delete(String id);
}

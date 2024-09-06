package com.calcpal.verbaldiagnosisservice.service;

import com.calcpal.verbaldiagnosisservice.DTO.VerbalQuestionDTO;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

public interface VerbalQuestionService {
    ResponseEntity<?> add(VerbalQuestionDTO questionBankDTO);

    ResponseEntity<?> getRandom(Long id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(String id, VerbalQuestionDTO questionBankDTO);

    ResponseEntity<?> delete(String id);
}

package com.calcpal.lexicaldiagnosisservice.service;

import com.calcpal.lexicaldiagnosisservice.DTO.LexicalQuestionDTO;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

public interface LexicalQuestionService {
    ResponseEntity<?> add(LexicalQuestionDTO questionDTO);

    ResponseEntity<?> getRandom(Long id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(String id, LexicalQuestionDTO questionDTO);

    ResponseEntity<?> delete(String id);
}

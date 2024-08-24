package com.calcpal.lexicaldiagnosisservice.service;

import com.calcpal.lexicaldiagnosisservice.DTO.LexicalQuestionDTO;

import org.springframework.http.ResponseEntity;

public interface LexicalQuestionService {
    ResponseEntity<?> add(LexicalQuestionDTO questionDTO);

    ResponseEntity<?> getRandom(Long id);

    ResponseEntity<?> getAll(int page, int size);

    ResponseEntity<?> update(String id, LexicalQuestionDTO questionDTO);

    ResponseEntity<?> delete(String id);
}

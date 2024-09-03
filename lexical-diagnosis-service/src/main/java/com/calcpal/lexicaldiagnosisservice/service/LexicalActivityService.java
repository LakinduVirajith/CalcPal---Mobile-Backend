package com.calcpal.lexicaldiagnosisservice.service;

import com.calcpal.lexicaldiagnosisservice.DTO.LexicalActivityDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LexicalActivityService {
    ResponseEntity<?> add(LexicalActivityDTO activityDTO);

    ResponseEntity<?> addAll(List<LexicalActivityDTO> activityDTOList);

    ResponseEntity<?> getRandom(Long id, String language);

    ResponseEntity<?> getAll(int page, int size);

    ResponseEntity<?> update(String id, LexicalActivityDTO activityDTO);

    ResponseEntity<?> delete(String id);
}

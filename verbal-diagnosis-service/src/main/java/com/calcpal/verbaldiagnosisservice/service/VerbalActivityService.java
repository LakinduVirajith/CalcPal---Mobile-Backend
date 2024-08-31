package com.calcpal.verbaldiagnosisservice.service;

import com.calcpal.verbaldiagnosisservice.DTO.VerbalActivityDTO;
import org.springframework.http.ResponseEntity;

public interface VerbalActivityService {
    ResponseEntity<?> add(VerbalActivityDTO activityDTO);

    ResponseEntity<?> getRandom(Long id, String language);

    ResponseEntity<?> getAll(int page, int size);

    ResponseEntity<?> update(String id, VerbalActivityDTO activityDTO);

    ResponseEntity<?> delete(String id);
}

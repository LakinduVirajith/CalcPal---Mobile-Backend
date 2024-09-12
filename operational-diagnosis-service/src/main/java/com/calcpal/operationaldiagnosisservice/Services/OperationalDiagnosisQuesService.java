package com.calcpal.operationaldiagnosisservice.Services;

import com.calcpal.operationaldiagnosisservice.DTO.QuestionDTO;
import org.springframework.http.ResponseEntity;

public interface OperationalDiagnosisQuesService {
    ResponseEntity<?> add(QuestionDTO questionDTO);

    ResponseEntity<?> getRandom(Long id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(String id, QuestionDTO questionDTO);

    ResponseEntity<?> delete(String id);
}

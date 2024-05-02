package com.calcpal.lexicaldiagnosisservice.service;

import com.calcpal.lexicaldiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.lexicaldiagnosisservice.collection.DiagnosisResult;
import org.springframework.http.ResponseEntity;

public interface DiagnosisResultService {
    ResponseEntity<?> add(DiagnosisResult lexicalDiagnosis);

    ResponseEntity<?> get(String email);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(DiagnosisResult lexicalDiagnosis);

    ResponseEntity<?> updateLabel(DiagnosisLabelDTO diagnosisLabelDTO);
}

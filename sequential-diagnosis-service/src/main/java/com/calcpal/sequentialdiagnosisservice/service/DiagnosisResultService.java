package com.calcpal.sequentialdiagnosisservice.service;

import com.calcpal.sequentialdiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.sequentialdiagnosisservice.collection.DiagnosisResult;
import org.springframework.http.ResponseEntity;

public interface DiagnosisResultService {
    ResponseEntity<?> add(DiagnosisResult sequentialDiagnosis);

    ResponseEntity<?> get(String email);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(DiagnosisResult sequentialDiagnosis);

    ResponseEntity<?> updateLabel(DiagnosisLabelDTO diagnosisLabelDTO);
}

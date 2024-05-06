package com.calcpal.visualdiagnosisservice.service;

import com.calcpal.visualdiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.visualdiagnosisservice.collection.DiagnosisResult;
import org.springframework.http.ResponseEntity;

public interface DiagnosisResultService {
    ResponseEntity<?> add(DiagnosisResult lexicalDiagnosis);

    ResponseEntity<?> get(String email);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(DiagnosisResult lexicalDiagnosis);

    ResponseEntity<?> updateLabel(DiagnosisLabelDTO diagnosisLabelDTO);
}

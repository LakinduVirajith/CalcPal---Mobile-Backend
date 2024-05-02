package com.calcpal.verbaldiagnosisservice.service;

import com.calcpal.verbaldiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.verbaldiagnosisservice.collection.DiagnosisResult;
import org.springframework.http.ResponseEntity;

public interface DiagnosisResultService {
    ResponseEntity<?> add(DiagnosisResult verbalDiagnosis);

    ResponseEntity<?> get(String email);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(DiagnosisResult verbalDiagnosis);

    ResponseEntity<?> updateLabel(DiagnosisLabelDTO diagnosisLabelDTO);
}

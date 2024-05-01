package com.calcpal.verbaldiagnosisservice.service;

import com.calcpal.verbaldiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.verbaldiagnosisservice.collection.VerbalDiagnosis;
import org.springframework.http.ResponseEntity;

public interface VerbalDiagnosisService {
    ResponseEntity<?> add(VerbalDiagnosis verbalDiagnosis);

    ResponseEntity<?> get(String email);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(VerbalDiagnosis verbalDiagnosis);

    ResponseEntity<?> updateLabel(DiagnosisLabelDTO diagnosisLabelDTO);
}

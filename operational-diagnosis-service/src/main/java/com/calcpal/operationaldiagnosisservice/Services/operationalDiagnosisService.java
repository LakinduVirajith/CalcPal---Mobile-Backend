package com.calcpal.operationaldiagnosisservice.Services;

import com.calcpal.operationaldiagnosisservice.DTO.DiagnosisDTO;
import com.calcpal.operationaldiagnosisservice.Collections.operationalDiagnosis;
import org.springframework.http.ResponseEntity;

public interface operationalDiagnosisService {
    ResponseEntity<?> add(operationalDiagnosis OperationalDiagnosis);

    ResponseEntity<?> get(String email);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(operationalDiagnosis OperationalDiagnosis);

    ResponseEntity<?> updateLabel(DiagnosisDTO diagnosisLabelDTO);
}
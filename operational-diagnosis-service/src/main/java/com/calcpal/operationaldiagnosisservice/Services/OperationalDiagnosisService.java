package com.calcpal.operationaldiagnosisservice.Services;

import com.calcpal.operationaldiagnosisservice.DTO.DiagnosisDTO;
import com.calcpal.operationaldiagnosisservice.Collections.OperationalDiagnosis;
import org.springframework.http.ResponseEntity;

public interface OperationalDiagnosisService {
    ResponseEntity<?> add(OperationalDiagnosis OperationalDiagnosis);

    ResponseEntity<?> get(String email);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(OperationalDiagnosis OperationalDiagnosis);

    ResponseEntity<?> updateLabel(DiagnosisDTO diagnosisLabelDTO);
}
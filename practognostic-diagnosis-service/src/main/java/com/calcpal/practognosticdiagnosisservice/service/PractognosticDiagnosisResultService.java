package com.calcpal.practognosticdiagnosisservice.service;

import com.calcpal.practognosticdiagnosisservice.DTO.PractognosticDiagnosisLabelDTO;
import com.calcpal.practognosticdiagnosisservice.collection.DiagnosisResultPractognostic;
import org.springframework.http.ResponseEntity;

public interface PractognosticDiagnosisResultService {
    ResponseEntity<?> add(DiagnosisResultPractognostic practognosticDiagnosis);

    ResponseEntity<?> get(String email);

    ResponseEntity<?> update(DiagnosisResultPractognostic practognosticDiagnosis);

    ResponseEntity<?> updateLabel(PractognosticDiagnosisLabelDTO practognosticDiagnosisLabelDTO);

    ResponseEntity<?> getAll();
}

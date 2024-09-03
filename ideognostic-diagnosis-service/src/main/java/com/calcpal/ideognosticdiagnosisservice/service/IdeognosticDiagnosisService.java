package com.calcpal.ideognosticdiagnosisservice.service;

import com.calcpal.ideognosticdiagnosisservice.DTO.DiagnosisDTO;
import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticDiagnosis;
import org.springframework.http.ResponseEntity;

public interface IdeognosticDiagnosisService {
    ResponseEntity<?> add(IdeognosticDiagnosis lexicalDiagnosis);

    ResponseEntity<?> get(String email);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(IdeognosticDiagnosis lexicalDiagnosis);

    ResponseEntity<?> updateLabel(DiagnosisDTO diagnosisLabelDTO);
}

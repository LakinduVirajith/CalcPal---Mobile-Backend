package com.calcpal.graphicaldiagnosisservice.service;


import com.calcpal.graphicaldiagnosisservice.DTO.GraphicalDiagnosisLabelDTO;
import com.calcpal.graphicaldiagnosisservice.collection.DiagnosisResultGraphical;
import org.springframework.http.ResponseEntity;

public interface GraphicalDiagnosisResultService {
    ResponseEntity<?> add(DiagnosisResultGraphical lexicalDiagnosis);

    ResponseEntity<?> get(String email);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(DiagnosisResultGraphical graphicalDiagnosis);

    ResponseEntity<?> updateLabel(GraphicalDiagnosisLabelDTO diagnosisLabelDTO);
}

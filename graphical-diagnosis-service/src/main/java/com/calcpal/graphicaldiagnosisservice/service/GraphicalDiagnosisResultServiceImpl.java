package com.calcpal.graphicaldiagnosisservice.service;

import com.calcpal.graphicaldiagnosisservice.DTO.GraphicalDiagnosisLabelDTO;
import com.calcpal.graphicaldiagnosisservice.collection.DiagnosisResultGraphical;
import com.calcpal.graphicaldiagnosisservice.repository.GraphicalDiagnosisResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GraphicalDiagnosisResultServiceImpl implements GraphicalDiagnosisResultService {

    private final GraphicalDiagnosisResultRepository diagnosisResultRepository;

    @Override
    public ResponseEntity<?> add(DiagnosisResultGraphical graphicalDiagnosis) {
        Optional<DiagnosisResultGraphical> optionalDiagnosis = diagnosisResultRepository.findById(graphicalDiagnosis.getUserEmail());

        if(optionalDiagnosis.isPresent()){
            DiagnosisResultGraphical diagnosisResult = mappingDiagnosisResult(graphicalDiagnosis, optionalDiagnosis.get());
            diagnosisResultRepository.save(diagnosisResult);
            return ResponseEntity.ok().body("Diagnosis data updated successfully");
        }else{
            diagnosisResultRepository.save(graphicalDiagnosis);
            return ResponseEntity.status(HttpStatus.CREATED).body("Diagnosis result inserted successfully");
        }
    }

    private static DiagnosisResultGraphical mappingDiagnosisResult(DiagnosisResultGraphical diagnosisResult, DiagnosisResultGraphical existingResult) {
        // MAPPING QUESTION DATA
        existingResult.setTimeSeconds(diagnosisResult.getTimeSeconds());
        existingResult.setQ1(diagnosisResult.getQ1());
        existingResult.setQ2(diagnosisResult.getQ2());
        existingResult.setQ3(diagnosisResult.getQ3());
        existingResult.setQ4(diagnosisResult.getQ4());
        existingResult.setQ5(diagnosisResult.getQ5());
        existingResult.setTotalScore(diagnosisResult.getTotalScore());

        if(existingResult.getLabel() != null){
            existingResult.setLabel(diagnosisResult.getLabel());
        }

        return diagnosisResult;
    }

    @Override
    public ResponseEntity<?> get(String email) {
        Optional<DiagnosisResultGraphical> optionalVerbalDiagnosis = diagnosisResultRepository.findById(email);

        // NOT FOUND EXCEPTION HANDLE
        if(optionalVerbalDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the given user");
        }
        DiagnosisResultGraphical diagnosis = optionalVerbalDiagnosis.get();

        return ResponseEntity.ok().body(diagnosis);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<DiagnosisResultGraphical> diagnosisList = diagnosisResultRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (diagnosisList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis are currently available in the collection");
        }

        return ResponseEntity.ok().body(diagnosisList);
    }

    @Override
    public ResponseEntity<?> update(DiagnosisResultGraphical graphicalDiagnosis) {
        Optional<DiagnosisResultGraphical> optionalVerbalDiagnosis = diagnosisResultRepository.findById(graphicalDiagnosis.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the provided user");
        }

        DiagnosisResultGraphical diagnosisResult = mappingDiagnosisResult(graphicalDiagnosis, optionalVerbalDiagnosis.get());
        diagnosisResultRepository.save(diagnosisResult);

        return ResponseEntity.ok().body("Diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(GraphicalDiagnosisLabelDTO diagnosisLabelDTO) {
        Optional<DiagnosisResultGraphical> optionalVerbalDiagnosis = diagnosisResultRepository.findById(diagnosisLabelDTO.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the provided user");
        }
        DiagnosisResultGraphical diagnosis = optionalVerbalDiagnosis.get();

        // MAPPING QUESTION DATA AND SAVE
        diagnosis.setLabel(diagnosisLabelDTO.getLabel());
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("Diagnosis label updated successfully");
    }
}

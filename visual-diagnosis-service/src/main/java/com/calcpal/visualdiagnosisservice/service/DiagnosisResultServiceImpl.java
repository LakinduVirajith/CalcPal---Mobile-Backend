package com.calcpal.visualdiagnosisservice.service;

import com.calcpal.visualdiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.visualdiagnosisservice.collection.DiagnosisResult;
import com.calcpal.visualdiagnosisservice.repository.DiagnosisResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiagnosisResultServiceImpl implements DiagnosisResultService {

    private final DiagnosisResultRepository diagnosisResultRepository;

    @Override
    public ResponseEntity<?> add(DiagnosisResult visualDiagnosis) {
        Optional<DiagnosisResult> optionalDiagnosis = diagnosisResultRepository.findById(visualDiagnosis.getUserEmail());

        if(optionalDiagnosis.isPresent()){
            DiagnosisResult diagnosisResult = mappingDiagnosisResult(visualDiagnosis, optionalDiagnosis.get());
            diagnosisResultRepository.save(diagnosisResult);
            return ResponseEntity.ok().body("Diagnosis data updated successfully");
        }else{
            diagnosisResultRepository.save(visualDiagnosis);
            return ResponseEntity.status(HttpStatus.CREATED).body("Diagnosis result inserted successfully");
        }
    }

    private static DiagnosisResult mappingDiagnosisResult(DiagnosisResult diagnosisResult, DiagnosisResult existingResult) {
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
        Optional<DiagnosisResult> optionalVerbalDiagnosis = diagnosisResultRepository.findById(email);

        // NOT FOUND EXCEPTION HANDLE
        if(optionalVerbalDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the given user");
        }
        DiagnosisResult diagnosis = optionalVerbalDiagnosis.get();

        return ResponseEntity.ok().body(diagnosis);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<DiagnosisResult> diagnosisList = diagnosisResultRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (diagnosisList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis are currently available in the collection");
        }

        return ResponseEntity.ok().body(diagnosisList);
    }

    @Override
    public ResponseEntity<?> update(DiagnosisResult visualDiagnosis) {
        Optional<DiagnosisResult> optionalVerbalDiagnosis = diagnosisResultRepository.findById(visualDiagnosis.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the provided user");
        }

        DiagnosisResult diagnosisResult = mappingDiagnosisResult(visualDiagnosis, optionalVerbalDiagnosis.get());
        diagnosisResultRepository.save(diagnosisResult);

        return ResponseEntity.ok().body("Diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(DiagnosisLabelDTO diagnosisLabelDTO) {
        Optional<DiagnosisResult> optionalVerbalDiagnosis = diagnosisResultRepository.findById(diagnosisLabelDTO.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the provided user");
        }
        DiagnosisResult diagnosis = optionalVerbalDiagnosis.get();

        // MAPPING QUESTION DATA AND SAVE
        diagnosis.setLabel(diagnosisLabelDTO.getLabel());
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("Diagnosis label updated successfully");
    }
}

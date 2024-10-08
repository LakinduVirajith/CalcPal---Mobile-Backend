package com.calcpal.lexicaldiagnosisservice.service;

import com.calcpal.lexicaldiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.lexicaldiagnosisservice.collection.DiagnosisResult;
import com.calcpal.lexicaldiagnosisservice.repository.DiagnosisResultRepository;
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
    public ResponseEntity<?> add(DiagnosisResult lexicalDiagnosis) {
        Optional<DiagnosisResult> optionalDiagnosis = diagnosisResultRepository.findById(lexicalDiagnosis.getUserEmail());

        if(optionalDiagnosis.isPresent()){
            DiagnosisResult diagnosisResult = mappingDiagnosisResult(lexicalDiagnosis, optionalDiagnosis.get());
            diagnosisResultRepository.save(diagnosisResult);
            return ResponseEntity.ok().body("Diagnosis data updated successfully");
        }else{
            diagnosisResultRepository.save(lexicalDiagnosis);
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
        Optional<DiagnosisResult> optionalLexicalDiagnosis = diagnosisResultRepository.findById(email);

        // NOT FOUND EXCEPTION HANDLE
        if(optionalLexicalDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the given user");
        }
        DiagnosisResult diagnosis = optionalLexicalDiagnosis.get();

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
    public ResponseEntity<?> update(DiagnosisResult lexicalDiagnosis) {
        Optional<DiagnosisResult> optionalLexicalDiagnosis = diagnosisResultRepository.findById(lexicalDiagnosis.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalLexicalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the provided user");
        }

        DiagnosisResult diagnosisResult = mappingDiagnosisResult(lexicalDiagnosis, optionalLexicalDiagnosis.get());
        diagnosisResultRepository.save(diagnosisResult);

        return ResponseEntity.ok().body("Diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(DiagnosisLabelDTO diagnosisLabelDTO) {
        Optional<DiagnosisResult> optionalLexicalDiagnosis = diagnosisResultRepository.findById(diagnosisLabelDTO.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalLexicalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the provided user");
        }
        DiagnosisResult diagnosis = optionalLexicalDiagnosis.get();

        // MAPPING QUESTION DATA AND SAVE
        diagnosis.setLabel(diagnosisLabelDTO.getLabel());
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("Diagnosis label updated successfully");
    }
}

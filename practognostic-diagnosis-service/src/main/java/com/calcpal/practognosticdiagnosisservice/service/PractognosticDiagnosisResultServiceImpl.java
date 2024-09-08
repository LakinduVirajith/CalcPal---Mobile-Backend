package com.calcpal.practognosticdiagnosisservice.service;

import com.calcpal.practognosticdiagnosisservice.DTO.PractognosticDiagnosisLabelDTO;
import com.calcpal.practognosticdiagnosisservice.collection.DiagnosisResultPractognostic;
import com.calcpal.practognosticdiagnosisservice.repository.PractognosticDiagnosisResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PractognosticDiagnosisResultServiceImpl implements PractognosticDiagnosisResultService {

    private final PractognosticDiagnosisResultRepository practognosticDiagnosisResultRepository;
    @Override
    public ResponseEntity<?> add(DiagnosisResultPractognostic practognosticDiagnosis) {
        Optional<DiagnosisResultPractognostic> optionalDiagnosis = practognosticDiagnosisResultRepository.findById(practognosticDiagnosis.getUserEmail());

        if(optionalDiagnosis.isPresent()){
            DiagnosisResultPractognostic diagnosisResult = mappingDiagnosisResult(practognosticDiagnosis, optionalDiagnosis.get());
            practognosticDiagnosisResultRepository.save(diagnosisResult);
            return ResponseEntity.ok().body("Diagnosis data updated successfully");
        }else{
            practognosticDiagnosisResultRepository.save(practognosticDiagnosis);
            return ResponseEntity.status(HttpStatus.CREATED).body("Diagnosis result inserted successfully");
        }
    }

    private static DiagnosisResultPractognostic mappingDiagnosisResult(DiagnosisResultPractognostic diagnosisResult, DiagnosisResultPractognostic existingResult) {
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
        Optional<DiagnosisResultPractognostic> optionalVerbalDiagnosis = practognosticDiagnosisResultRepository.findById(email);

        // NOT FOUND EXCEPTION HANDLE
        if(optionalVerbalDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the given user");
        }
        DiagnosisResultPractognostic diagnosis = optionalVerbalDiagnosis.get();

        return ResponseEntity.ok().body(diagnosis);
    }

    @Override
    public ResponseEntity<?> update(DiagnosisResultPractognostic practognosticDiagnosis) {
        Optional<DiagnosisResultPractognostic> optionalVerbalDiagnosis = practognosticDiagnosisResultRepository.findById(practognosticDiagnosis.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the provided user");
        }

        DiagnosisResultPractognostic diagnosisResult = mappingDiagnosisResult(practognosticDiagnosis, optionalVerbalDiagnosis.get());
        practognosticDiagnosisResultRepository.save(diagnosisResult);

        return ResponseEntity.ok().body("Diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(PractognosticDiagnosisLabelDTO practognosticDiagnosisLabelDTO) {
        Optional<DiagnosisResultPractognostic> optionalVerbalDiagnosis = practognosticDiagnosisResultRepository.findById(practognosticDiagnosisLabelDTO.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the provided user");
        }
        DiagnosisResultPractognostic diagnosis = optionalVerbalDiagnosis.get();

        // MAPPING QUESTION DATA AND SAVE
        diagnosis.setLabel(practognosticDiagnosisLabelDTO.getLabel());
        practognosticDiagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("Diagnosis label updated successfully");
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<DiagnosisResultPractognostic> diagnosisList = practognosticDiagnosisResultRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (diagnosisList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis are currently available in the collection");
        }

        return ResponseEntity.ok().body(diagnosisList);
    }


}

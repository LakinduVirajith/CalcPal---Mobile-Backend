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
        Optional<DiagnosisResult> diagnosis = diagnosisResultRepository.findById(lexicalDiagnosis.getUserEmail());

        if(diagnosis.isPresent()){
            update(diagnosis.get());
        }else{
            diagnosisResultRepository.save(lexicalDiagnosis);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("diagnosis result inserted successfully");
    }

    @Override
    public ResponseEntity<?> get(String email) {
        Optional<DiagnosisResult> optionalLexicalDiagnosis = diagnosisResultRepository.findById(email);

        // NOT FOUND EXCEPTION HANDLE
        if(optionalLexicalDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis found for the given user");
        }
        DiagnosisResult diagnosis = optionalLexicalDiagnosis.get();

        return ResponseEntity.ok().body(diagnosis);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<DiagnosisResult> diagnosisList = diagnosisResultRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (diagnosisList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis are currently available in the collection");
        }

        return ResponseEntity.ok().body(diagnosisList);
    }

    @Override
    public ResponseEntity<?> update(DiagnosisResult lexicalDiagnosis) {
        Optional<DiagnosisResult> optionalLexicalDiagnosis = diagnosisResultRepository.findById(lexicalDiagnosis.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalLexicalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis found for the provided user");
        }
        DiagnosisResult diagnosis = optionalLexicalDiagnosis.get();

        // MAPPING QUESTION DATA
        diagnosis.setTimeSeconds(lexicalDiagnosis.getTimeSeconds());
        diagnosis.setQ1(lexicalDiagnosis.getQ1());
        diagnosis.setQ2(lexicalDiagnosis.getQ2());
        diagnosis.setQ3(lexicalDiagnosis.getQ3());
        diagnosis.setQ4(lexicalDiagnosis.getQ4());
        diagnosis.setQ5(lexicalDiagnosis.getQ5());
        diagnosis.setTotalScore(lexicalDiagnosis.getTotalScore());

        if(diagnosis.getLabel() != null){
            diagnosis.setLabel(lexicalDiagnosis.getLabel());
        }
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(DiagnosisLabelDTO diagnosisLabelDTO) {
        Optional<DiagnosisResult> optionalLexicalDiagnosis = diagnosisResultRepository.findById(diagnosisLabelDTO.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalLexicalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis found for the provided user");
        }
        DiagnosisResult diagnosis = optionalLexicalDiagnosis.get();

        // MAPPING QUESTION DATA AND SAVE
        diagnosis.setLabel(diagnosisLabelDTO.getLabel());
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("diagnosis label updated successfully");
    }
}

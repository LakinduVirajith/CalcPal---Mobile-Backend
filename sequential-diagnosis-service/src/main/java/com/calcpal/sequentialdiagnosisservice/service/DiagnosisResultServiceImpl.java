package com.calcpal.sequentialdiagnosisservice.service;

import com.calcpal.sequentialdiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.sequentialdiagnosisservice.collection.DiagnosisResult;
import com.calcpal.sequentialdiagnosisservice.repository.DiagnosisResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiagnosisResultServiceImpl implements DiagnosisResultService{

    private final DiagnosisResultRepository diagnosisResultRepository;

    @Override
    public ResponseEntity<?> add(DiagnosisResult sequentialDiagnosis){
        Optional<DiagnosisResult> diagnosis = diagnosisResultRepository.findById(sequentialDiagnosis.getUserEmail());

        if(diagnosis.isPresent()){
            update(diagnosis.get());
        }else{
            diagnosisResultRepository.save(sequentialDiagnosis);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Diagnosis Result Added Successfully");
    }

    @Override
    public ResponseEntity<?> get(String email) {
        Optional<DiagnosisResult> optionalSequentialDiagnosis = diagnosisResultRepository.findById(email);

        if(optionalSequentialDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Diagnosis Result Not Found For User");
        }
        DiagnosisResult diagnosis = optionalSequentialDiagnosis.get();

        return ResponseEntity.ok().body(diagnosis);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<DiagnosisResult> diagnosisList = diagnosisResultRepository.findAll();

        if(diagnosisList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Diagnosis Result Not Found In Collection");
        }
        return ResponseEntity.ok().body(diagnosisList);
    }

    @Override
    public ResponseEntity<?> update(DiagnosisResult sequentialDiagnosis) {
        Optional<DiagnosisResult> optionalSequentialDiagnosis = diagnosisResultRepository.findById(sequentialDiagnosis.getUserEmail());

        if (optionalSequentialDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Diagnosis Result Not Found For User");
        }

        DiagnosisResult diagnosis = optionalSequentialDiagnosis.get();

        diagnosis.setTimeSeconds(sequentialDiagnosis.getTimeSeconds());
        diagnosis.setQ1(sequentialDiagnosis.getQ1());
        diagnosis.setQ2(sequentialDiagnosis.getQ2());
        diagnosis.setQ3(sequentialDiagnosis.getQ3());
        diagnosis.setQ4(sequentialDiagnosis.getQ4());
        diagnosis.setQ5(sequentialDiagnosis.getQ5());
        diagnosis.setTotalScore(sequentialDiagnosis.getTotalScore());

        if(diagnosis.getLabel() != null){
            diagnosis.setLabel(sequentialDiagnosis.getLabel());
        }
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("Diagnosis Result Updated Successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(DiagnosisLabelDTO diagnosisLabelDTO) {
        Optional<DiagnosisResult> optionalSequentialDiagnosis = diagnosisResultRepository.findById(diagnosisLabelDTO.getUserEmail());

        if (optionalSequentialDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Diagnosis Result Not Found For User");
        }
        DiagnosisResult diagnosis = optionalSequentialDiagnosis.get();

        diagnosis.setLabel(diagnosisLabelDTO.getLabel());
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("Diagnosis Result Updated Successfully");
    }
}

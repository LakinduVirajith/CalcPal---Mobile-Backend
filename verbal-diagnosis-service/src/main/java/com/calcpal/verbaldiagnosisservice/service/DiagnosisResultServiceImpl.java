package com.calcpal.verbaldiagnosisservice.service;

import com.calcpal.verbaldiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.verbaldiagnosisservice.collection.DiagnosisResult;
import com.calcpal.verbaldiagnosisservice.repository.DiagnosisResultRepository;
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
    public ResponseEntity<?> add(DiagnosisResult verbalDiagnosis) {
        Optional<DiagnosisResult> diagnosis = diagnosisResultRepository.findById(verbalDiagnosis.getUserEmail());

        if(diagnosis.isPresent()){
            update(diagnosis.get());
        }else{
            diagnosisResultRepository.save(verbalDiagnosis);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("diagnosis result inserted successfully");
    }

    @Override
    public ResponseEntity<?> get(String email) {
        Optional<DiagnosisResult> optionalVerbalDiagnosis = diagnosisResultRepository.findById(email);

        // NOT FOUND EXCEPTION HANDLE
        if(optionalVerbalDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis found for the given user");
        }
        DiagnosisResult diagnosis = optionalVerbalDiagnosis.get();

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
    public ResponseEntity<?> update(DiagnosisResult verbalDiagnosis) {
        Optional<DiagnosisResult> optionalVerbalDiagnosis = diagnosisResultRepository.findById(verbalDiagnosis.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis found for the provided user");
        }
        DiagnosisResult diagnosis = optionalVerbalDiagnosis.get();

        // MAPPING QUESTION DATA
        diagnosis.setTimeSeconds(verbalDiagnosis.getTimeSeconds());
        diagnosis.setQ1(verbalDiagnosis.getQ1());
        diagnosis.setQ2(verbalDiagnosis.getQ2());
        diagnosis.setQ3(verbalDiagnosis.getQ3());
        diagnosis.setQ4(verbalDiagnosis.getQ4());
        diagnosis.setQ5(verbalDiagnosis.getQ5());
        diagnosis.setTotalScore(verbalDiagnosis.getTotalScore());

        if(diagnosis.getLabel() != null){
            diagnosis.setLabel(verbalDiagnosis.getLabel());
        }
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(DiagnosisLabelDTO diagnosisLabelDTO) {
        Optional<DiagnosisResult> optionalVerbalDiagnosis = diagnosisResultRepository.findById(diagnosisLabelDTO.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis found for the provided user");
        }
        DiagnosisResult diagnosis = optionalVerbalDiagnosis.get();

        // MAPPING QUESTION DATA AND SAVE
        diagnosis.setLabel(diagnosisLabelDTO.getLabel());
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("diagnosis label updated successfully");
    }
}

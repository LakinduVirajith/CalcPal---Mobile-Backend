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
        Optional<DiagnosisResultPractognostic> diagnosis = practognosticDiagnosisResultRepository.findById(practognosticDiagnosis.getUserEmail());

        if(diagnosis.isPresent()){
            update(diagnosis.get());
        }else{
            practognosticDiagnosisResultRepository.save(practognosticDiagnosis);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("diagnosis result inserted successfully");
    }

    @Override
    public ResponseEntity<?> get(String email) {
        Optional<DiagnosisResultPractognostic> optionalVerbalDiagnosis = practognosticDiagnosisResultRepository.findById(email);

        // NOT FOUND EXCEPTION HANDLE
        if(optionalVerbalDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis found for the given user");
        }
        DiagnosisResultPractognostic diagnosis = optionalVerbalDiagnosis.get();

        return ResponseEntity.ok().body(diagnosis);
    }

    @Override
    public ResponseEntity<?> update(DiagnosisResultPractognostic practognosticDiagnosis) {
        Optional<DiagnosisResultPractognostic> optionalVerbalDiagnosis = practognosticDiagnosisResultRepository.findById(practognosticDiagnosis.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis found for the provided user");
        }
        DiagnosisResultPractognostic diagnosis = optionalVerbalDiagnosis.get();

        // MAPPING QUESTION DATA
        diagnosis.setTimeSeconds(practognosticDiagnosis.getTimeSeconds());
        diagnosis.setQ1(practognosticDiagnosis.getQ1());
        diagnosis.setQ2(practognosticDiagnosis.getQ2());
        diagnosis.setQ3(practognosticDiagnosis.getQ3());
        diagnosis.setQ4(practognosticDiagnosis.getQ4());
        diagnosis.setQ5(practognosticDiagnosis.getQ5());
        diagnosis.setTotalScore(practognosticDiagnosis.getTotalScore());

        if(diagnosis.getLabel() != null){
            diagnosis.setLabel(practognosticDiagnosis.getLabel());
        }
        practognosticDiagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(PractognosticDiagnosisLabelDTO practognosticDiagnosisLabelDTO) {
        Optional<DiagnosisResultPractognostic> optionalVerbalDiagnosis = practognosticDiagnosisResultRepository.findById(practognosticDiagnosisLabelDTO.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis found for the provided user");
        }
        DiagnosisResultPractognostic diagnosis = optionalVerbalDiagnosis.get();

        // MAPPING QUESTION DATA AND SAVE
        diagnosis.setLabel(practognosticDiagnosisLabelDTO.getLabel());
        practognosticDiagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("diagnosis label updated successfully");
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<DiagnosisResultPractognostic> diagnosisList = practognosticDiagnosisResultRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (diagnosisList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis are currently available in the collection");
        }

        return ResponseEntity.ok().body(diagnosisList);
    }


}

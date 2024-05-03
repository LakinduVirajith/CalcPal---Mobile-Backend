package com.calcpal.operationaldiagnosisservice.Services;

import com.calcpal.operationaldiagnosisservice.Collections.operationalDiagnosis;
import com.calcpal.operationaldiagnosisservice.Repositary.operationalDiagnosisRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class operationalDiagnosisServiceImpl implements operationalDiagnosisService {

    private final operationalDiagnosisRepo OperationalDiagnosisRepositary;

    @Override
    public ResponseEntity<?> add(operationalDiagnosis OperationalDiagnosis) {
        Optional<operationalDiagnosis> diagnosis = OperationalDiagnosisRepositary.findById(operationalDiagnosis.getUserEmail());

        if(diagnosis.isPresent()){
            update(diagnosis.get());
        }else{
            OperationalDiagnosisRepositary.save(OperationalDiagnosis);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Operational diagnosis result inserted successfully");
    }

    @Override
    public ResponseEntity<?> get(String email) {
        Optional<operationalDiagnosis> OperationalDiagnosis = OperationalDiagnosisRepositary.findById(email);
        operationalDiagnosis diagnosis;

        if(OperationalDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Operational diagnosis found for this student");
        }else{
            diagnosis = OperationalDiagnosis.get();
        }

        return ResponseEntity.ok().body(diagnosis);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<operationalDiagnosis> operationalDiagnosedList = OperationalDiagnosisRepositary.findAll();

        if (operationalDiagnosedList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Operational diagnosis are currently available");
        }

        return ResponseEntity.ok().body(operationalDiagnosedList);
    }

    @Override
    public ResponseEntity<?> update(operationalDiagnosis OperationalDiagnosis) {
        Optional<operationalDiagnosis> OperationalDiagnosisObj = operationalDiagnosisRepo.findById(operationalDiagnosis.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (OperationalDiagnosisObj.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Operational diagnosis found for this student");
        }
        operationalDiagnosis diagnosis = OperationalDiagnosisObj.get();

        // MAPPING QUESTION DATA
        diagnosis.setTimeSeconds(lexicalDiagnosis.getTimeSeconds());
        diagnosis.setQ1(lexicalDiagnosis.getQ1());
        diagnosis.setQ2(lexicalDiagnosis.getQ2());
        diagnosis.setQ3(lexicalDiagnosis.getQ3());
        diagnosis.setQ4(lexicalDiagnosis.getQ4());
        diagnosis.setQ5(lexicalDiagnosis.getQ5());
        diagnosis.setTotalScore(lexicalDiagnosis.getTotalScore());

        if(diagnosis.getLabel() != null){
            diagnosis.setLabel(operationalDiagnosis.getLabel());
        }
        operationalDiagnosisRepo.save(diagnosis);

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


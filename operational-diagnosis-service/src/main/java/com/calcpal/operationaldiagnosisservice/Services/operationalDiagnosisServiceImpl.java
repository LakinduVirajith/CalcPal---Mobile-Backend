package com.calcpal.operationaldiagnosisservice.Services;

import com.calcpal.operationaldiagnosisservice.Collections.operationalDiagnosis;
import com.calcpal.operationaldiagnosisservice.DTO.DiagnosisDTO;
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
        Optional<operationalDiagnosis> diagnosis = OperationalDiagnosisRepositary.findById(OperationalDiagnosis.getUserEmail());

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
        Optional<operationalDiagnosis> OperationalDiagnosisObj = OperationalDiagnosisRepositary.findById(OperationalDiagnosis.getUserEmail());

        if (OperationalDiagnosisObj.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Operational diagnosis found for this student");
        }
        operationalDiagnosis diagnosis = OperationalDiagnosisObj.get();

        diagnosis.setQuizTimeTaken(OperationalDiagnosis.getQuizTimeTaken());
        diagnosis.setQ1(OperationalDiagnosis.getQ1());
        diagnosis.setQ2(OperationalDiagnosis.getQ2());
        diagnosis.setQ3(OperationalDiagnosis.getQ3());
        diagnosis.setQ4(OperationalDiagnosis.getQ4());
        diagnosis.setQ5(OperationalDiagnosis.getQ5());
        diagnosis.setScore(OperationalDiagnosis.getScore());

        if(diagnosis.getDiagnosis() != null){
            diagnosis.setDiagnosis(OperationalDiagnosis.getDiagnosis());
        }
        OperationalDiagnosisRepositary.save(diagnosis);

        return ResponseEntity.ok().body("diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(DiagnosisDTO diagnosisLabelDTO) {
        Optional<operationalDiagnosis> optionalOperationalDiagnosis = OperationalDiagnosisRepositary.findById(diagnosisLabelDTO.getUserEmail());

        if (optionalOperationalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Operational diagnosis found for the provided user");
        }
        operationalDiagnosis diagnosis = optionalOperationalDiagnosis.get();

        diagnosis.setDiagnosis(diagnosisLabelDTO.getLabel());
        OperationalDiagnosisRepositary.save(diagnosis);

        return ResponseEntity.ok().body("Operational Diagnosis updated successfully");
    }

}


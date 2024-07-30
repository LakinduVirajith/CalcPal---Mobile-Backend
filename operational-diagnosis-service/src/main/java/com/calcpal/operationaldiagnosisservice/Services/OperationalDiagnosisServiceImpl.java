package com.calcpal.operationaldiagnosisservice.Services;

import com.calcpal.operationaldiagnosisservice.Collections.OperationalDiagnosis;
import com.calcpal.operationaldiagnosisservice.DTO.DiagnosisDTO;
import com.calcpal.operationaldiagnosisservice.Repositary.OperationalDiagnosisRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperationalDiagnosisServiceImpl implements OperationalDiagnosisService {

    private final OperationalDiagnosisRepo diagnosisRepo;

    @Override
    public ResponseEntity<?> add(OperationalDiagnosis OperationalDiagnosis) {
        Optional<com.calcpal.operationaldiagnosisservice.Collections.OperationalDiagnosis> diagnosis = diagnosisRepo.findById(OperationalDiagnosis.getUserEmail());

        if(diagnosis.isPresent()){
            update(diagnosis.get());
        }else{
            diagnosisRepo.save(OperationalDiagnosis);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Operational diagnosis result inserted successfully");
    }

    @Override
    public ResponseEntity<?> get(String email) {
        Optional<OperationalDiagnosis> OperationalDiagnosis = diagnosisRepo.findById(email);
        com.calcpal.operationaldiagnosisservice.Collections.OperationalDiagnosis diagnosis;

        if(OperationalDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Operational diagnosis found for this student");
        }else{
            diagnosis = OperationalDiagnosis.get();
        }

        return ResponseEntity.ok().body(diagnosis);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<OperationalDiagnosis> operationalDiagnosedList = diagnosisRepo.findAll();

        if (operationalDiagnosedList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Operational diagnosis are currently available");
        }

        return ResponseEntity.ok().body(operationalDiagnosedList);
    }

    @Override
    public ResponseEntity<?> update(OperationalDiagnosis OperationalDiagnosis) {
        Optional<com.calcpal.operationaldiagnosisservice.Collections.OperationalDiagnosis> OperationalDiagnosisObj = diagnosisRepo.findById(OperationalDiagnosis.getUserEmail());

        if (OperationalDiagnosisObj.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Operational diagnosis found for this student");
        }
        com.calcpal.operationaldiagnosisservice.Collections.OperationalDiagnosis diagnosis = OperationalDiagnosisObj.get();

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
        diagnosisRepo.save(diagnosis);

        return ResponseEntity.ok().body("diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(DiagnosisDTO diagnosisLabelDTO) {
        Optional<OperationalDiagnosis> optionalOperationalDiagnosis = diagnosisRepo.findById(diagnosisLabelDTO.getUserEmail());

        if (optionalOperationalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Operational diagnosis found for the provided user");
        }
        OperationalDiagnosis diagnosis = optionalOperationalDiagnosis.get();

        diagnosis.setDiagnosis(diagnosisLabelDTO.getLabel());
        diagnosisRepo.save(diagnosis);

        return ResponseEntity.ok().body("Operational Diagnosis updated successfully");
    }

}


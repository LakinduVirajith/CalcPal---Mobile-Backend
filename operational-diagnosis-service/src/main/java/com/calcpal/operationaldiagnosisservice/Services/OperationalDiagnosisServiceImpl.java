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
        Optional<OperationalDiagnosis> optionalDiagnosis = diagnosisRepo.findById(OperationalDiagnosis.getUserEmail());

        if(optionalDiagnosis.isPresent()){
            OperationalDiagnosis diagnosisResult = mappingDiagnosisResult(OperationalDiagnosis, optionalDiagnosis.get());
            diagnosisRepo.save(diagnosisResult);
            return ResponseEntity.ok().body("Diagnosis data updated successfully");
        }else{
            diagnosisRepo.save(OperationalDiagnosis);
            return ResponseEntity.status(HttpStatus.CREATED).body("Diagnosis result inserted successfully");
        }
    }

    private static OperationalDiagnosis mappingDiagnosisResult(OperationalDiagnosis diagnosisResult, OperationalDiagnosis existingResult) {
        // MAPPING QUESTION DATA
        existingResult.setQuizTimeTaken(diagnosisResult.getQuizTimeTaken());
        existingResult.setQ1(diagnosisResult.getQ1());
        existingResult.setQ2(diagnosisResult.getQ2());
        existingResult.setQ3(diagnosisResult.getQ3());
        existingResult.setQ4(diagnosisResult.getQ4());
        existingResult.setQ5(diagnosisResult.getQ5());
        existingResult.setScore(diagnosisResult.getScore());

        if(existingResult.getDiagnosis() != null){
            existingResult.setDiagnosis(diagnosisResult.getDiagnosis());
        }

        return diagnosisResult;
    }

    @Override
    public ResponseEntity<?> get(String email) {
        Optional<OperationalDiagnosis> optionalVerbalDiagnosis = diagnosisRepo.findById(email);

        // NOT FOUND EXCEPTION HANDLE
        if(optionalVerbalDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the given user");
        }
        OperationalDiagnosis diagnosis = optionalVerbalDiagnosis.get();

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
        Optional<OperationalDiagnosis> optionalVerbalDiagnosis = diagnosisRepo.findById(OperationalDiagnosis.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the provided user");
        }

        OperationalDiagnosis diagnosisResult = mappingDiagnosisResult(OperationalDiagnosis, optionalVerbalDiagnosis.get());
        diagnosisRepo.save(diagnosisResult);

        return ResponseEntity.ok().body("Diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(DiagnosisDTO diagnosisLabelDTO) {
        Optional<OperationalDiagnosis> optionalVerbalDiagnosis = diagnosisRepo.findById(diagnosisLabelDTO.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the provided user");
        }
        OperationalDiagnosis diagnosis = optionalVerbalDiagnosis.get();

        // MAPPING QUESTION DATA AND SAVE
        diagnosis.setDiagnosis(diagnosisLabelDTO.getLabel());
        diagnosisRepo.save(diagnosis);

        return ResponseEntity.ok().body("Diagnosis label updated successfully");
    }

}


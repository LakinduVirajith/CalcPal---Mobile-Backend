package com.calcpal.ideognosticdiagnosisservice.service;

import com.calcpal.ideognosticdiagnosisservice.DTO.DiagnosisDTO;
import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticDiagnosis;
import com.calcpal.ideognosticdiagnosisservice.repository.IdeognosticDiagnosesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdeognosticDiagnosisServiceImpl implements IdeognosticDiagnosisService {

    private final IdeognosticDiagnosesRepo diagnosisResultRepository;

    @Override
    public ResponseEntity<?> add(IdeognosticDiagnosis ideognosticDiagnosis) {
        Optional<IdeognosticDiagnosis> optionalDiagnosis = diagnosisResultRepository.findById(IdeognosticDiagnosis.getUserEmail());

        if(optionalDiagnosis.isPresent()){
            IdeognosticDiagnosis diagnosisResult = mappingDiagnosisResult(ideognosticDiagnosis, optionalDiagnosis.get());
            diagnosisResultRepository.save(diagnosisResult);
            return ResponseEntity.ok().body("Diagnosis data updated successfully");
        }else{
            diagnosisResultRepository.save(ideognosticDiagnosis);
            return ResponseEntity.status(HttpStatus.CREATED).body("Diagnosis result inserted successfully");
        }
    }

    private static IdeognosticDiagnosis mappingDiagnosisResult(IdeognosticDiagnosis diagnosisResult, IdeognosticDiagnosis existingResult) {
        // MAPPING QUESTION DATA
        existingResult.setTimeSeconds(diagnosisResult.getTimeSeconds());
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
        Optional<IdeognosticDiagnosis> Diagnosis = diagnosisResultRepository.findById(email);

        if(Diagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Ideognostic diagnosis found for the given user");
        }
        IdeognosticDiagnosis diagnosis = Diagnosis.get();

        return ResponseEntity.ok().body(diagnosis);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<IdeognosticDiagnosis> diagnosisList = diagnosisResultRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (diagnosisList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis are currently available in the collection");
        }

        return ResponseEntity.ok().body(diagnosisList);
    }

    @Override
    public ResponseEntity<?> update(IdeognosticDiagnosis ideognosticDiagnosis) {
        Optional<IdeognosticDiagnosis> optionalVerbalDiagnosis = diagnosisResultRepository.findById(ideognosticDiagnosis.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the provided user");
        }

        IdeognosticDiagnosis diagnosisResult = mappingDiagnosisResult(ideognosticDiagnosis, optionalVerbalDiagnosis.get());
        diagnosisResultRepository.save(diagnosisResult);

        return ResponseEntity.ok().body("Diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(DiagnosisDTO diagnosisLabelDTO) {
        Optional<IdeognosticDiagnosis> optionalVerbalDiagnosis = diagnosisResultRepository.findById(diagnosisLabelDTO.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diagnosis found for the provided user");
        }
        IdeognosticDiagnosis diagnosis = optionalVerbalDiagnosis.get();

        // MAPPING QUESTION DATA AND SAVE
        diagnosis.setDiagnosis(diagnosisLabelDTO.getLabel());
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("Diagnosis label updated successfully");
    }
}

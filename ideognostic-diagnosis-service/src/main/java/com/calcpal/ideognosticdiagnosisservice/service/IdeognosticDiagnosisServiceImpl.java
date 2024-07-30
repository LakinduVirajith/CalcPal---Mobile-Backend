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
        Optional<IdeognosticDiagnosis> diagnosis = diagnosisResultRepository.findById(ideognosticDiagnosis.getUserEmail());

        if(diagnosis.isPresent()){
            update(diagnosis.get());
        }else{
            diagnosisResultRepository.save(ideognosticDiagnosis);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Ideognostic diagnosis result inserted successfully");
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

        if (diagnosisList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Ideognostic diagnosis are currently available in the collection");
        }

        return ResponseEntity.ok().body(diagnosisList);
    }

    @Override
    public ResponseEntity<?> update(IdeognosticDiagnosis ideognosticDiagnosis) {
        Optional<IdeognosticDiagnosis> optionalVerbalDiagnosis = diagnosisResultRepository.findById(ideognosticDiagnosis.getUserEmail());

        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Ideognostic diagnosis found for the provided user");
        }
        IdeognosticDiagnosis diagnosis = optionalVerbalDiagnosis.get();

        diagnosis.setTimeSeconds(ideognosticDiagnosis.getTimeSeconds());
        diagnosis.setQ1(ideognosticDiagnosis.getQ1());
        diagnosis.setQ2(ideognosticDiagnosis.getQ2());
        diagnosis.setQ3(ideognosticDiagnosis.getQ3());
        diagnosis.setQ4(ideognosticDiagnosis.getQ4());
        diagnosis.setQ5(ideognosticDiagnosis.getQ5());
        diagnosis.setScore(ideognosticDiagnosis.getScore());

        if(diagnosis.getDiagnosis() != null){
            diagnosis.setDiagnosis(ideognosticDiagnosis.getDiagnosis());
        }
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("Ideognostic diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(DiagnosisDTO diagnosisLabelDTO) {
        Optional<IdeognosticDiagnosis> Diagnosis = diagnosisResultRepository.findById(diagnosisLabelDTO.getUserEmail());

        if (Diagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Ideognostic diagnosis found for the provided user");
        }
        IdeognosticDiagnosis diagnosis = Diagnosis.get();

        diagnosis.setDiagnosis(diagnosisLabelDTO.getLabel());
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("Ideognostic diagnosis label updated successfully");
    }
}

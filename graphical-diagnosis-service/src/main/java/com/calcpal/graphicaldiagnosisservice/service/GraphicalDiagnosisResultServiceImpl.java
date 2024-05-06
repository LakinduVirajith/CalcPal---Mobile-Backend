package com.calcpal.graphicaldiagnosisservice.service;

import com.calcpal.graphicaldiagnosisservice.DTO.GraphicalDiagnosisLabelDTO;
import com.calcpal.graphicaldiagnosisservice.collection.DiagnosisResultGraphical;
import com.calcpal.graphicaldiagnosisservice.repository.GraphicalDiagnosisResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GraphicalDiagnosisResultServiceImpl implements GraphicalDiagnosisResultService {

    private final GraphicalDiagnosisResultRepository diagnosisResultRepository;

    @Override
    public ResponseEntity<?> add(DiagnosisResultGraphical graphicalDiagnosis) {
        Optional<DiagnosisResultGraphical> diagnosis = diagnosisResultRepository.findById(graphicalDiagnosis.getUserEmail());

        if(diagnosis.isPresent()){
            update(diagnosis.get());
        }else{
            diagnosisResultRepository.save(graphicalDiagnosis);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("diagnosis result inserted successfully");
    }

    @Override
    public ResponseEntity<?> get(String email) {
        Optional<DiagnosisResultGraphical> optionalVerbalDiagnosis = diagnosisResultRepository.findById(email);

        // NOT FOUND EXCEPTION HANDLE
        if(optionalVerbalDiagnosis.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis found for the given user");
        }
        DiagnosisResultGraphical diagnosis = optionalVerbalDiagnosis.get();

        return ResponseEntity.ok().body(diagnosis);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<DiagnosisResultGraphical> diagnosisList = diagnosisResultRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (diagnosisList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis are currently available in the collection");
        }

        return ResponseEntity.ok().body(diagnosisList);
    }

    @Override
    public ResponseEntity<?> update(DiagnosisResultGraphical graphicalDiagnosis) {
        Optional<DiagnosisResultGraphical> optionalVerbalDiagnosis = diagnosisResultRepository.findById(graphicalDiagnosis.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis found for the provided user");
        }
        DiagnosisResultGraphical diagnosis = optionalVerbalDiagnosis.get();

        // MAPPING QUESTION DATA
        diagnosis.setTimeSeconds(graphicalDiagnosis.getTimeSeconds());
        diagnosis.setQ1(graphicalDiagnosis.getQ1());
        diagnosis.setQ2(graphicalDiagnosis.getQ2());
        diagnosis.setQ3(graphicalDiagnosis.getQ3());
        diagnosis.setQ4(graphicalDiagnosis.getQ4());
        diagnosis.setQ5(graphicalDiagnosis.getQ5());
        diagnosis.setTotalScore(graphicalDiagnosis.getTotalScore());

        if(diagnosis.getLabel() != null){
            diagnosis.setLabel(graphicalDiagnosis.getLabel());
        }
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("diagnosis data updated successfully");
    }

    @Override
    public ResponseEntity<?> updateLabel(GraphicalDiagnosisLabelDTO diagnosisLabelDTO) {
        Optional<DiagnosisResultGraphical> optionalVerbalDiagnosis = diagnosisResultRepository.findById(diagnosisLabelDTO.getUserEmail());

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no diagnosis found for the provided user");
        }
        DiagnosisResultGraphical diagnosis = optionalVerbalDiagnosis.get();

        // MAPPING QUESTION DATA AND SAVE
        diagnosis.setLabel(diagnosisLabelDTO.getLabel());
        diagnosisResultRepository.save(diagnosis);

        return ResponseEntity.ok().body("diagnosis label updated successfully");
    }
}

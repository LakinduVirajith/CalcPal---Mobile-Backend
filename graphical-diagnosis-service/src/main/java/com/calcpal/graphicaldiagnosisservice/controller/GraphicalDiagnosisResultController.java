package com.calcpal.graphicaldiagnosisservice.controller;


import com.calcpal.graphicaldiagnosisservice.DTO.GraphicalDiagnosisLabelDTO;
import com.calcpal.graphicaldiagnosisservice.DTO.GraphicalQuestionDTO;
import com.calcpal.graphicaldiagnosisservice.collection.DiagnosisResultGraphical;
import com.calcpal.graphicaldiagnosisservice.collection.GraphicalQuestion;
import com.calcpal.graphicaldiagnosisservice.service.GraphicalDiagnosisResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/graphical/diagnosis")
@Tag(name = "Diagnosis Result Controllers")
@RequiredArgsConstructor
public class GraphicalDiagnosisResultController {

    private final GraphicalDiagnosisResultService diagnosisResultService;

    @PostMapping("/")
    @Operation(summary = "Add Graphical Diagnosis Result", description = "Add a new Graphical diagnosis result to the database.")
    public ResponseEntity<?> add(@Valid @RequestBody DiagnosisResultGraphical graphicalDiagnosis) {
        return diagnosisResultService.add(graphicalDiagnosis);
    }

    @GetMapping("/")
    @Operation(summary = "Get Graphical Diagnosis Result by Email", description = "Retrieve a Graphical diagnosis result from the database by email.")
    public ResponseEntity<?> get(@RequestParam String email) {
        return diagnosisResultService.get(email);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Graphical Diagnoses Result", description = "Retrieve all Graphical diagnoses result from the database.")
    public ResponseEntity<?> getAll() {
        return diagnosisResultService.getAll();
    }

    @PutMapping("/")
    @Operation(summary = "Update Graphical Diagnosis Result", description = "Update a Graphical diagnosis result in the database.")
    public ResponseEntity<?> update(@Valid @RequestBody DiagnosisResultGraphical graphicalDiagnosis) {
        return diagnosisResultService.update(graphicalDiagnosis);
    }

    @PutMapping("/label")
    @Operation(summary = "Update Diagnosis Result Label", description = "Update a diagnosis result label in the database.")
    public ResponseEntity<?> updateLabel(@Valid @RequestBody GraphicalDiagnosisLabelDTO diagnosisLabelDTO) {
        return diagnosisResultService.updateLabel(diagnosisLabelDTO);
    }
}

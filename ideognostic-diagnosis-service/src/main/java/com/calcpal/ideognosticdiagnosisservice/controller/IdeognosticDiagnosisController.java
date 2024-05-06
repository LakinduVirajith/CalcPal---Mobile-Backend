package com.calcpal.ideognosticdiagnosisservice.controller;

import com.calcpal.ideognosticdiagnosisservice.DTO.DiagnosisDTO;
import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticDiagnosis;
import com.calcpal.ideognosticdiagnosisservice.service.IdeognosticDiagnosisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ideognostic/diagnosis")
@Tag(name = "Ideognostic Diagnosis Result Controllers")
@RequiredArgsConstructor
public class IdeognosticDiagnosisController {

    private final IdeognosticDiagnosisService diagnosisResultService;

    @PostMapping("/")
    @Operation(summary = "Add Ideognostic Diagnosis Result", description = "Add a new lexical diagnosis result to the database.")
    public ResponseEntity<?> add(@Valid @RequestBody IdeognosticDiagnosis ideognosticDiagnosis) {
        return diagnosisResultService.add(ideognosticDiagnosis);
    }

    @GetMapping("/")
    @Operation(summary = "Get Ideognostic Diagnosis Result by Email", description = "Retrieve an Ideognostic diagnosis result from the database by email.")
    public ResponseEntity<?> get(@RequestParam String email) {
        return diagnosisResultService.get(email);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Ideognostic Diagnoses Result", description = "Retrieve all Ideognostic diagnoses result from the database.")
    public ResponseEntity<?> getAll() {
        return diagnosisResultService.getAll();
    }

    @PutMapping("/")
    @Operation(summary = "Update Ideognostic Diagnosis Result", description = "Update a Ideognostic diagnosis result in the database.")
    public ResponseEntity<?> update(@Valid @RequestBody IdeognosticDiagnosis ideognosticDiagnosis) {
        return diagnosisResultService.update(ideognosticDiagnosis);
    }

    @PutMapping("/label")
    @Operation(summary = "Update Diagnosis Result Label", description = "Update a diagnosis result label in the database.")
    public ResponseEntity<?> updateLabel(@Valid @RequestBody DiagnosisDTO diagnosisLabelDTO) {
        return diagnosisResultService.updateLabel(diagnosisLabelDTO);
    }
}

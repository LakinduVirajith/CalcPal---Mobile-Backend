package com.calcpal.practognosticdiagnosisservice.controller;

import com.calcpal.practognosticdiagnosisservice.DTO.PractognosticDiagnosisLabelDTO;
import com.calcpal.practognosticdiagnosisservice.collection.DiagnosisResultPractognostic;
import com.calcpal.practognosticdiagnosisservice.service.PractognosticDiagnosisResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/practognostic/diagnosis")
@Tag(name = "Diagnosis Result Controllers")
@RequiredArgsConstructor
public class PractognosticDiagnosisResultController {
    private final PractognosticDiagnosisResultService practognosticDiagnosisResultService;

    @PostMapping("/")
    @Operation(summary = "Add Practognostic Diagnosis Result", description = "Add a new Practognostic diagnosis result to the database.")
    public ResponseEntity<?> add(@Valid @RequestBody DiagnosisResultPractognostic practognosticDiagnosis) {
        return practognosticDiagnosisResultService.add(practognosticDiagnosis);
    }

    @GetMapping("/")
    @Operation(summary = "Get Lexical Diagnosis Result by Email", description = "Retrieve a lexical diagnosis result from the database by email.")
    public ResponseEntity<?> get(@RequestParam String email) {
        return practognosticDiagnosisResultService.get(email);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Lexical Diagnoses Result", description = "Retrieve all lexical diagnoses result from the database.")
    public ResponseEntity<?> getAll() {
        return practognosticDiagnosisResultService.getAll();
    }

    @PutMapping("/")
    @Operation(summary = "Update Lexical Diagnosis Result", description = "Update a lexical diagnosis result in the database.")
    public ResponseEntity<?> update(@Valid @RequestBody DiagnosisResultPractognostic practognosticDiagnosis) {
        return practognosticDiagnosisResultService.update(practognosticDiagnosis);
    }

    @PutMapping("/label")
    @Operation(summary = "Update Diagnosis Result Label", description = "Update a diagnosis result label in the database.")
    public ResponseEntity<?> updateLabel(@Valid @RequestBody PractognosticDiagnosisLabelDTO practognosticDiagnosisLabelDTO) {
        return practognosticDiagnosisResultService.updateLabel(practognosticDiagnosisLabelDTO);
    }


}

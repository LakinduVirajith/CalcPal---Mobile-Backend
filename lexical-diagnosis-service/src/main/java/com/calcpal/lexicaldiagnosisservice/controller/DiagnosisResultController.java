package com.calcpal.lexicaldiagnosisservice.controller;

import com.calcpal.lexicaldiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.lexicaldiagnosisservice.collection.DiagnosisResult;
import com.calcpal.lexicaldiagnosisservice.service.DiagnosisResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lexical/diagnosis")
@Tag(name = "Diagnosis Result Controllers")
@RequiredArgsConstructor
public class DiagnosisResultController {

    private final DiagnosisResultService diagnosisResultService;

    @PostMapping("/")
    @Operation(summary = "Add Lexical Diagnosis Result", description = "Add a new lexical diagnosis result to the database.")
    public ResponseEntity<?> add(@Valid @RequestBody DiagnosisResult lexicalDiagnosis) {
        return diagnosisResultService.add(lexicalDiagnosis);
    }

    @GetMapping("/")
    @Operation(summary = "Get Lexical Diagnosis Result by Email", description = "Retrieve a lexical diagnosis result from the database by email.")
    public ResponseEntity<?> get(@RequestParam String email) {
        return diagnosisResultService.get(email);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Lexical Diagnoses Result", description = "Retrieve all lexical diagnoses result from the database.")
    public ResponseEntity<?> getAll() {
        return diagnosisResultService.getAll();
    }

    @PutMapping("/")
    @Operation(summary = "Update Lexical Diagnosis Result", description = "Update a lexical diagnosis result in the database.")
    public ResponseEntity<?> update(@Valid @RequestBody DiagnosisResult lexicalDiagnosis) {
        return diagnosisResultService.update(lexicalDiagnosis);
    }

    @PutMapping("/label")
    @Operation(summary = "Update Diagnosis Result Label", description = "Update a diagnosis result label in the database.")
    public ResponseEntity<?> updateLabel(@Valid @RequestBody DiagnosisLabelDTO diagnosisLabelDTO) {
        return diagnosisResultService.updateLabel(diagnosisLabelDTO);
    }
}

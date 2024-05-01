package com.calcpal.verbaldiagnosisservice.controller;

import com.calcpal.verbaldiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.verbaldiagnosisservice.collection.VerbalDiagnosis;
import com.calcpal.verbaldiagnosisservice.service.VerbalDiagnosisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/verbal/diagnosis")
@Tag(name = "Verbal Diagnosis Controllers")
@RequiredArgsConstructor
public class VerbalDiagnosisController {

    private final VerbalDiagnosisService diagnosisService;

    @PostMapping("/")
    @Operation(summary = "Add Verbal Diagnosis", description = "Add a new verbal diagnosis to the database.")
    public ResponseEntity<?> add(@Valid @RequestBody VerbalDiagnosis verbalDiagnosis) {
        return diagnosisService.add(verbalDiagnosis);
    }

    @GetMapping("/")
    @Operation(summary = "Get Verbal Diagnosis by Email", description = "Retrieve a verbal diagnosis from the database by email.")
    public ResponseEntity<?> get(@RequestBody String email) {
        return diagnosisService.get(email);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Verbal Diagnoses", description = "Retrieve all verbal diagnoses from the database.")
    public ResponseEntity<?> getAll() {
        return diagnosisService.getAll();
    }

    @PutMapping("/")
    @Operation(summary = "Update Verbal Diagnosis", description = "Update a verbal diagnosis in the database.")
    public ResponseEntity<?> update(@Valid @RequestBody VerbalDiagnosis verbalDiagnosis) {
        return diagnosisService.update(verbalDiagnosis);
    }

    @PutMapping("/label")
    @Operation(summary = "Update Diagnosis Label", description = "Update a diagnosis label in the database.")
    public ResponseEntity<?> updateLabel(@Valid @RequestBody DiagnosisLabelDTO diagnosisLabelDTO) {
        return diagnosisService.updateLabel(diagnosisLabelDTO);
    }
}

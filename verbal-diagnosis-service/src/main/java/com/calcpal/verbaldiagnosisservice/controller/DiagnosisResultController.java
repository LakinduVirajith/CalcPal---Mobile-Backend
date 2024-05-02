package com.calcpal.verbaldiagnosisservice.controller;

import com.calcpal.verbaldiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.verbaldiagnosisservice.collection.DiagnosisResult;
import com.calcpal.verbaldiagnosisservice.service.DiagnosisResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/verbal/diagnosis")
@Tag(name = "Diagnosis Result Controllers")
@RequiredArgsConstructor
public class DiagnosisResultController {

    private final DiagnosisResultService diagnosisResultService;

    @PostMapping("/")
    @Operation(summary = "Add Verbal Diagnosis Result", description = "Add a new verbal diagnosis result to the database.")
    public ResponseEntity<?> add(@Valid @RequestBody DiagnosisResult verbalDiagnosis) {
        return diagnosisResultService.add(verbalDiagnosis);
    }

    @GetMapping("/")
    @Operation(summary = "Get Verbal Diagnosis Result by Email", description = "Retrieve a verbal diagnosis result from the database by email.")
    public ResponseEntity<?> get(@RequestBody String email) {
        return diagnosisResultService.get(email);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Verbal Diagnoses Result", description = "Retrieve all verbal diagnoses result from the database.")
    public ResponseEntity<?> getAll() {
        return diagnosisResultService.getAll();
    }

    @PutMapping("/")
    @Operation(summary = "Update Verbal Diagnosis Result", description = "Update a verbal diagnosis result in the database.")
    public ResponseEntity<?> update(@Valid @RequestBody DiagnosisResult verbalDiagnosis) {
        return diagnosisResultService.update(verbalDiagnosis);
    }

    @PutMapping("/label")
    @Operation(summary = "Update Diagnosis Result Label", description = "Update a diagnosis result label in the database.")
    public ResponseEntity<?> updateLabel(@Valid @RequestBody DiagnosisLabelDTO diagnosisLabelDTO) {
        return diagnosisResultService.updateLabel(diagnosisLabelDTO);
    }
}

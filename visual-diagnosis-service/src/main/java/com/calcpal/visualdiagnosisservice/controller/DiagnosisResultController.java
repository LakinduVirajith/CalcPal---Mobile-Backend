package com.calcpal.visualdiagnosisservice.controller;

import com.calcpal.visualdiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.visualdiagnosisservice.collection.DiagnosisResult;
import com.calcpal.visualdiagnosisservice.service.DiagnosisResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/visual/diagnosis")
@Tag(name = "Diagnosis Result Controllers")
@RequiredArgsConstructor
public class DiagnosisResultController {

    private final DiagnosisResultService diagnosisResultService;

    @PostMapping("/")
    @Operation(summary = "Add Visual Spatial Diagnosis Result", description = "Add a new Visual Spatial diagnosis result to the database.")
    public ResponseEntity<?> add(@Valid @RequestBody DiagnosisResult visualDiagnosis) {
        return diagnosisResultService.add(visualDiagnosis);
    }

    @GetMapping("/")
    @Operation(summary = "Get Visual Spatial Diagnosis Result by Email", description = "Retrieve a Visual Spatial diagnosis result from the database by email.")
    public ResponseEntity<?> get(@RequestParam String email) {
        return diagnosisResultService.get(email);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Visual Spatial Diagnoses Result", description = "Retrieve all Visual Spatial diagnoses result from the database.")
    public ResponseEntity<?> getAll() {
        return diagnosisResultService.getAll();
    }

    @PutMapping("/")
    @Operation(summary = "Update Visual Spatial Diagnosis Result", description = "Update a Visual Spatial diagnosis result in the database.")
    public ResponseEntity<?> update(@Valid @RequestBody DiagnosisResult visualDiagnosis) {
        return diagnosisResultService.update(visualDiagnosis);
    }

    @PutMapping("/label")
    @Operation(summary = "Update Diagnosis Result Label", description = "Update a diagnosis result label in the database.")
    public ResponseEntity<?> updateLabel(@Valid @RequestBody DiagnosisLabelDTO diagnosisLabelDTO) {
        return diagnosisResultService.updateLabel(diagnosisLabelDTO);
    }
}

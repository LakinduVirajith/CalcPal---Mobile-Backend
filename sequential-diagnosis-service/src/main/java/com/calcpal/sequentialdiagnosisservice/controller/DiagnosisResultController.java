package com.calcpal.sequentialdiagnosisservice.controller;

import com.calcpal.sequentialdiagnosisservice.DTO.DiagnosisLabelDTO;
import com.calcpal.sequentialdiagnosisservice.collection.DiagnosisResult;
import com.calcpal.sequentialdiagnosisservice.service.DiagnosisResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sequential/diagnosis")
@Tag(name = "Diagnosis Result Controllers")
@RequiredArgsConstructor
public class DiagnosisResultController {

    private final DiagnosisResultService diagnosisResultService;

    @PostMapping("/")
    @Operation(summary = "Add Sequential Diagnosis Result", description = "Add a new Sequential diagnosis result to the database")
    public ResponseEntity<?> add(@Valid @RequestBody DiagnosisResult sequentialDiagnosis) {
        return diagnosisResultService.add(sequentialDiagnosis);
    }

    @GetMapping("/")
    @Operation(summary = "Get Sequential Diagnosis Result By Email" , description = "Retrieve a sequential diagnosis result from the database by email.")
    public ResponseEntity<?> getByEmail(@RequestParam String email) {
        return diagnosisResultService.get(email);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Sequential Diagnosis Result" , description = "Retrieve all sequential diagnosis result from the database.")
    public ResponseEntity<?> getAll() {
        return diagnosisResultService.getAll();
    }
    @PutMapping("/")
    @Operation(summary = "Update Sequential Diagnosis Result", description = "Update a Sequential diagnosis result in the database.")
    public ResponseEntity<?> update(@Valid @RequestBody DiagnosisResult sequentialDiagnosis) {
        return diagnosisResultService.update(sequentialDiagnosis);
    }
    @PutMapping("/label")
    @Operation(summary = "Update Diagnosis Result Label", description = "Update a diagnosis result label in the database.")
    public ResponseEntity<?> updateLabel(@Valid @RequestBody DiagnosisLabelDTO diagnosisLabelDTO) {
        return diagnosisResultService.updateLabel(diagnosisLabelDTO);
    }
}

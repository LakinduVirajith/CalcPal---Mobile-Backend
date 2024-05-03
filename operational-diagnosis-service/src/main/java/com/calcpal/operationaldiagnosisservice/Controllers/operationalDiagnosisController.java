package com.calcpal.operationaldiagnosisservice.Controllers;

import com.calcpal.operationaldiagnosisservice.Collections.operationalDiagnosis;
import com.calcpal.operationaldiagnosisservice.DTO.DiagnosisDTO;
import com.calcpal.operationaldiagnosisservice.Services.operationalDiagnosisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operational/diagnosis")
@Tag(name = "Operational Diagnosis Result Controllers")
@RequiredArgsConstructor
public class operationalDiagnosisController {

    private final operationalDiagnosisService diagnosisResultService;

    @PostMapping("/")
    @Operation(summary = "Add new Operational Diagnosis Result", description = "Add a new diagnosis of Operational Dyscalculia to the database.")
    public ResponseEntity<?> add(@Valid @RequestBody operationalDiagnosis Diagnosis) {
        return diagnosisResultService.add(Diagnosis);
    }

    @GetMapping("/")
    @Operation(summary = "Get a specific Operational Diagnosis Result by Email", description = "Retrieve a specific students Operational diagnosis result from the database by email.")
    public ResponseEntity<?> get(@RequestParam String email) {
        return diagnosisResultService.get(email);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Operational Diagnoses Result", description = "Retrieve all operatioanl diagnoses result from the database.")
    public ResponseEntity<?> getAll() {
        return diagnosisResultService.getAll();
    }

    @PutMapping("/")
    @Operation(summary = "Update Operational Diagnosis Result", description = "Update an Operational diagnosis result in the database.")
    public ResponseEntity<?> update(@Valid @RequestBody operationalDiagnosis Diagnosis) {
        return diagnosisResultService.update(Diagnosis);
    }

    @PutMapping("/label")
    @Operation(summary = "Update Diagnosis Result Label", description = "Update a diagnosis result label in the database.")
    public ResponseEntity<?> updateLabel(@Valid @RequestBody DiagnosisDTO diagnosisLabelDTO) {
        return diagnosisResultService.updateLabel(diagnosisLabelDTO);
    }
}


package com.calcpal.operationaldiagnosisservice.Controllers;

import com.calcpal.operationaldiagnosisservice.DTO.QuestionDTO;
import com.calcpal.operationaldiagnosisservice.Services.OperationalDiagnosisQuesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operational/questionbank")
@Tag(name = "Operational Diagnostic Question Bank Controllers")
@RequiredArgsConstructor
public class OperationalDiagnosisQuesController {

    private final OperationalDiagnosisQuesService questionBankService;

    @PostMapping("/")
    @Operation(summary = "Add an Operational Diagnostic Question", description = "Add a new Operational diagnostic question to the question bank.")
    public ResponseEntity<?> add(@Valid @RequestBody QuestionDTO questionDTO) {
        return questionBankService.add(questionDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an Operational Diagnostic Question by ID", description = "Retrieve an Operational Diagnostic question from the question bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id) {
        return questionBankService.getRandom(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Operational Diagnostic Questions", description = "Retrieve all Operational Diagnostic questions from the question bank.")
    public ResponseEntity<?> getAll() {
        return questionBankService.getAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an Operational Diagnostic Question", description = "Update an Operational Diagnostic question in the question bank.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody QuestionDTO questionDTO) {
        return questionBankService.update(id, questionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Operational Diagnostic Question", description = "Delete an Operational Diagnostic question from the question bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return questionBankService.delete(id);
    }
}


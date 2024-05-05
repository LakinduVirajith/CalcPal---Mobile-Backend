package com.calcpal.sequentialdiagnosisservice.controller;

import com.calcpal.sequentialdiagnosisservice.DTO.SequentialQuestionDTO;
import com.calcpal.sequentialdiagnosisservice.service.SequentialQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sequential/quection")
@Tag(name = "Sequential Question Controller")
@RequiredArgsConstructor
public class SequentialQuestionController {

    private final SequentialQuestionService questionBankService;

    @PostMapping("/")
    @Operation(summary = "Add Sequential Question", description = "Add a new Sequential question to the question bank.")
    public ResponseEntity<?> add(@Valid @RequestBody SequentialQuestionDTO questionDTO) {
        return questionBankService.add(questionDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Sequential Question by ID", description = "Retrieve a Sequential question from the question bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id) {

        return questionBankService.getRandom(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Sequential Questions", description = "Retrieve all Sequential questions from the question bank.")
    public ResponseEntity<?> getAll() {

        return questionBankService.getAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Sequential Question", description = "Update a Sequential question in the question bank.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody SequentialQuestionDTO questionDTO) {
        return questionBankService.update(id, questionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Sequential Question", description = "Delete a Sequential question from the question bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {

        return questionBankService.delete(id);
    }
}

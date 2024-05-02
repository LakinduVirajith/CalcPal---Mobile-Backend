package com.calcpal.verbaldiagnosisservice.controller;

import com.calcpal.verbaldiagnosisservice.DTO.VerbalQuestionDTO;
import com.calcpal.verbaldiagnosisservice.service.VerbalQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/verbal/question")
@Tag(name = "Verbal Question Controllers")
@RequiredArgsConstructor
public class VerbalQuestionController {

    private final VerbalQuestionService questionBankService;

    @PostMapping("/")
    @Operation(summary = "Add Verbal Question", description = "Add a new verbal question to the question bank.")
    public ResponseEntity<?> add(@Valid @RequestBody VerbalQuestionDTO questionBankDTO) {
        return questionBankService.add(questionBankDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Verbal Question by ID", description = "Retrieve a verbal question from the question bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id) {
        return questionBankService.getRandom(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Verbal Questions", description = "Retrieve all verbal questions from the question bank.")
    public ResponseEntity<?> getAll() {
        return questionBankService.getAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Verbal Question", description = "Update a verbal question in the question bank.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody VerbalQuestionDTO questionBankDTO) {
        return questionBankService.update(id, questionBankDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Verbal Question", description = "Delete a verbal question from the question bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return questionBankService.delete(id);
    }
}

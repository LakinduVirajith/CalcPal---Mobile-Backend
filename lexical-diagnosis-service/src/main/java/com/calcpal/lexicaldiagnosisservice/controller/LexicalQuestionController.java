package com.calcpal.lexicaldiagnosisservice.controller;

import com.calcpal.lexicaldiagnosisservice.DTO.LexicalQuestionDTO;
import com.calcpal.lexicaldiagnosisservice.service.LexicalQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lexical/question")
@Tag(name = "Lexical Question Controllers")
@RequiredArgsConstructor
public class LexicalQuestionController {

    private final LexicalQuestionService questionBankService;

    @PostMapping("/")
    @Operation(summary = "Add Lexical Question", description = "Add a new lexical question to the question bank.")
    public ResponseEntity<?> add(@Valid @RequestBody LexicalQuestionDTO questionDTO) {
        return questionBankService.add(questionDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Lexical Question by ID", description = "Retrieve a lexical question from the question bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id) {
        return questionBankService.getRandom(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Lexical Questions", description = "Retrieve all lexical questions from the question bank.")
    public ResponseEntity<?> getAll() {
        return questionBankService.getAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Lexical Question", description = "Update a lexical question in the question bank.")
    public ResponseEntity<?> update(@PathVariable ObjectId id, @Valid @RequestBody LexicalQuestionDTO questionDTO) {
        return questionBankService.update(id, questionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Lexical Question", description = "Delete a lexical question from the question bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable ObjectId id) {
        return questionBankService.delete(id);
    }
}

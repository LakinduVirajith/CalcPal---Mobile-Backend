package com.calcpal.lexicaldiagnosisservice.controller;

import com.calcpal.lexicaldiagnosisservice.DTO.LexicalQuestionDTO;
import com.calcpal.lexicaldiagnosisservice.service.LexicalQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/all")
    @Operation(summary = "Add Lexical Questions", description = "Add a new lexical questions to the question bank.")
    public ResponseEntity<?> addAll(@Valid @RequestBody List<LexicalQuestionDTO> questionDTOList) {
        return questionBankService.addAll(questionDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Lexical Question by ID", description = "Retrieve a lexical question from the question bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id) {
        return questionBankService.getRandom(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Lexical Questions", description = "Retrieve all lexical questions from the question bank.")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return questionBankService.getAll(page, size);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Lexical Question", description = "Update a lexical question in the question bank.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody LexicalQuestionDTO questionDTO) {
        return questionBankService.update(id, questionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Lexical Question", description = "Delete a lexical question from the question bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return questionBankService.delete(id);
    }
}

package com.calcpal.graphicaldiagnosisservice.controller;

import com.calcpal.graphicaldiagnosisservice.DTO.GraphicalQuestionDTO;
import com.calcpal.graphicaldiagnosisservice.service.GraphicalQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/graphical/question")
@Tag(name = "Lexical Question Controllers")
@RequiredArgsConstructor
public class GraphicalQuestionController {

    private final GraphicalQuestionService questionBankService;

    @PostMapping("/")
    @Operation(summary = "Add Graphical Question", description = "Add a new Graphical question to the question bank.")
    public ResponseEntity<?> add(@Valid @RequestBody GraphicalQuestionDTO questionDTO) {
        return questionBankService.add(questionDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Graphical Question by ID", description = "Retrieve a Graphical question from the question bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id) {
        return questionBankService.getRandom(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Graphical Questions", description = "Retrieve all Graphical questions from the question bank.")
    public ResponseEntity<?> getAll() {
        return questionBankService.getAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Graphical Question", description = "Update a Graphical question in the question bank.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody GraphicalQuestionDTO questionDTO) {
        return questionBankService.update(id, questionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Graphical Question", description = "Delete a Graphical question from the question bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return questionBankService.delete(id);
    }
}

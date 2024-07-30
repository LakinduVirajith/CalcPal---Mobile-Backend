package com.calcpal.practognosticdiagnosisservice.controller;

import com.calcpal.practognosticdiagnosisservice.DTO.PractognosticQuestionDTO;
import com.calcpal.practognosticdiagnosisservice.service.PractognosticQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/practognostic/question")
@Tag(name = "Practognostic Question Controllers")
@RequiredArgsConstructor
public class PractognosticQuestionController {

    private final PractognosticQuestionService practognosticQuestionService;

    @PostMapping("/")
    @Operation(summary = "Add Practognostic Question", description = "Add a new practognostic question to the question bank.")
    public ResponseEntity<?> add(@Valid @RequestBody PractognosticQuestionDTO PractognosticQuestionDTO){
        return practognosticQuestionService.add(PractognosticQuestionDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Practognostic Question By ID",description = "Retrieve a lexical question from the question bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id) {
        return practognosticQuestionService.getRandom(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Practognostic Questions", description = "Retrieve all Practognostic questions from the question bank.")
    public ResponseEntity<?> getAll() {
        return practognosticQuestionService.getAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Practognostic Question", description = "Update a Practognostic question in the question bank.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody PractognosticQuestionDTO questionDTO) {
        return practognosticQuestionService.update(id, questionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Practognostic Question", description = "Delete a Practognostic question from the question bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return practognosticQuestionService.delete(id);
    }

}

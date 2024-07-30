package com.calcpal.visualdiagnosisservice.controller;


import com.calcpal.visualdiagnosisservice.DTO.VisualQuestionDTO;
import com.calcpal.visualdiagnosisservice.service.VisualQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/visual/question")
@Tag(name = "Visual Question Controllers")
@RequiredArgsConstructor
public class VisualQuestionController {

    private final VisualQuestionService questionBankService;

    @PostMapping("/")
    @Operation(summary = "Add Visual Spatial Question", description = "Add a new Visual Spatial question to the question bank.")
    public ResponseEntity<?> add(@Valid @RequestBody VisualQuestionDTO questionDTO) {
        return questionBankService.add(questionDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Visual Spatial Question by ID", description = "Retrieve a Visual Spatial question from the question bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id) {
        return questionBankService.getRandom(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Visual Spatial Questions", description = "Retrieve all Visual Spatial questions from the question bank.")
    public ResponseEntity<?> getAll() {
        return questionBankService.getAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Visual Spatial Question", description = "Update a Visual Spatial question in the question bank.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody VisualQuestionDTO questionDTO) {
        return questionBankService.update(id, questionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Visual Spatial Question", description = "Delete a Visual Spatial question from the question bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return questionBankService.delete(id);
    }
}

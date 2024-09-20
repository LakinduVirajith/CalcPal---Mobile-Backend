package com.calcpal.ideognosticdiagnosisservice.controller;

import com.calcpal.ideognosticdiagnosisservice.DTO.IdeognosticQuestionUploadDTO;
import com.calcpal.ideognosticdiagnosisservice.service.IdeognosticQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/ideognostic/question")
@Tag(name = "Ideognostic Question Controllers")
@RequiredArgsConstructor
public class IdeognosticQuestionController {

    private final IdeognosticQuestionService questionBankService;

    @PostMapping("/")
    @Operation(summary = "Add Ideognostic diagnosis Question", description = "Add a new Ideognostic question to the question bank.")
    public ResponseEntity<?> add(@RequestParam("file") MultipartFile image, @Valid  IdeognosticQuestionUploadDTO questionDTO) {
        return questionBankService.add(image, questionDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Ideognostic Question by ID", description = "Retrieve a Ideognostic question from the question bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id) {
        return questionBankService.getRandom(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Ideognostic Questions", description = "Retrieve all Ideognostic questions from the question bank.")
    public ResponseEntity<?> getAll() {
        return questionBankService.getAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Ideognostic Question", description = "Update a Ideognostic question in the question bank.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody IdeognosticQuestionUploadDTO questionDTO) {
        return questionBankService.update(id, questionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Ideognostic Question", description = "Delete a Ideognostic question from the question bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return questionBankService.delete(id);
    }
}

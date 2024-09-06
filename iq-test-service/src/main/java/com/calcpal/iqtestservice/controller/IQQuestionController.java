package com.calcpal.iqtestservice.controller;

import com.calcpal.iqtestservice.DTO.IQQuestionDTO;
import com.calcpal.iqtestservice.service.IQQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/iq/question")
@Tag(name = "IQ Question Controllers")
@RequiredArgsConstructor
public class IQQuestionController {

    private final IQQuestionService questionBankService;

    @PostMapping("/")
    @Operation(summary = "Add IQ Question", description = "Add a new iq question to the question bank.")
    public ResponseEntity<?> add(@Valid @RequestBody IQQuestionDTO questionDTO) {
        return questionBankService.add(questionDTO);
    }

    @PostMapping("/all")
    @Operation(summary = "Add IQ Questions", description = "Add a new iq questions to the question bank.")
    public ResponseEntity<?> addAll(@Valid @RequestBody List<IQQuestionDTO> questionDTOList) {
        return questionBankService.addAll(questionDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get IQ Question by ID", description = "Retrieve a iq question from the question bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id, @RequestParam String language) {
        return questionBankService.getRandom(id, language);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All IQ Questions", description = "Retrieve all iq questions from the question bank.")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return questionBankService.getAll(page, size);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update IQ Question", description = "Update a iq question in the question bank.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody IQQuestionDTO questionDTO) {
        return questionBankService.update(id, questionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete IQ Question", description = "Delete a iq question from the question bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return questionBankService.delete(id);
    }
}

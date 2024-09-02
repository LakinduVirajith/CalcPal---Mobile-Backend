package com.calcpal.lexicaldiagnosisservice.controller;

import com.calcpal.lexicaldiagnosisservice.DTO.LexicalActivityDTO;
import com.calcpal.lexicaldiagnosisservice.service.LexicalActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lexical/activity")
@Tag(name = "Lexical Activity Controllers")
@RequiredArgsConstructor
public class LexicalActivityController {

    private final LexicalActivityService activityBankService;

    @PostMapping("/")
    @Operation(summary = "Add Lexical Activity", description = "Add a new lexical activity to the activity bank.")
    public ResponseEntity<?> add(@Valid @RequestBody LexicalActivityDTO activityDTO) {
        return activityBankService.add(activityDTO);
    }

    @PostMapping("/all")
    @Operation(summary = "Add Lexical Activities", description = "Add a new lexical activities to the activity bank.")
    public ResponseEntity<?> addAll(@Valid @RequestBody List<LexicalActivityDTO> activityDTOList) {
        return activityBankService.addAll(activityDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Lexical Activity by ID", description = "Retrieve a lexical activity from the activity bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id, @RequestParam String language) {
        return activityBankService.getRandom(id, language);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Lexical Activities", description = "Retrieve all lexical activities from the activity bank.")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return activityBankService.getAll(page, size);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Lexical Activity", description = "Update a lexical activity in the activity bank.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody LexicalActivityDTO activityDTO) {
        return activityBankService.update(id, activityDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Lexical Activity", description = "Delete a lexical activity from the activity bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return activityBankService.delete(id);
    }
}

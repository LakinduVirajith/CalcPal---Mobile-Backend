package com.calcpal.verbaldiagnosisservice.controller;

import com.calcpal.verbaldiagnosisservice.DTO.VerbalActivityDTO;
import com.calcpal.verbaldiagnosisservice.service.VerbalActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/verbal/activity")
@Tag(name = "Verbal Activity Controllers")
@RequiredArgsConstructor
public class VerbalActivityController {

    private final VerbalActivityService activityBankService;

    @PostMapping("/")
    @Operation(summary = "Add Verbal Activity", description = "Add a new verbal activity to the activity bank.")
    public ResponseEntity<?> add(@Valid @RequestBody VerbalActivityDTO activityDTO) {
        return activityBankService.add(activityDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Verbal Activity by ID", description = "Retrieve a verbal activity from the activity bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id, @RequestParam String language) {
        return activityBankService.getRandom(id, language);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Verbal Activities", description = "Retrieve all verbal activities from the activity bank.")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return activityBankService.getAll(page, size);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Verbal Activity", description = "Update a verbal activity in the activity bank.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody VerbalActivityDTO activityDTO) {
        return activityBankService.update(id, activityDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Verbal Activity", description = "Delete a verbal activity from the activity bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return activityBankService.delete(id);
    }
}

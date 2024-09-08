package com.calcpal.practognosticdiagnosisservice.controller;

import com.calcpal.practognosticdiagnosisservice.DTO.PractognosticActivityDTO;
import com.calcpal.practognosticdiagnosisservice.service.PractognosticActivityService;
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
public class PractognosticActivityController {

    private final PractognosticActivityService activityBankService;

    @PostMapping("/")
    @Operation(summary = "Add  Practognostic Activity", description = "Add a new practognostic activity to the activity bank.")
    public ResponseEntity<?> add(@Valid @RequestBody PractognosticActivityDTO activityDTO) {
        return activityBankService.add(activityDTO);
    }

    @PostMapping("/all")
    @Operation(summary = "Add Practognostic Activities", description = "Add a new practognostic activities to the activity bank.")
    public ResponseEntity<?> addAll(@Valid @RequestBody List<PractognosticActivityDTO> activityDTOList) {
        return activityBankService.addAll(activityDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Practognostic Activity by ID", description = "Retrieve a practognostic activity from the activity bank by its ID.")
    public ResponseEntity<?> getRandom(@PathVariable Long id, @RequestParam String language) {
        return activityBankService.getRandom(id, language);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Practognostic Activities", description = "Retrieve all practognostic activities from the activity bank.")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return activityBankService.getAll(page, size);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Practognostic Activity", description = "Update a practognostic activity in the activity bank.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody PractognosticActivityDTO activityDTO) {
        return activityBankService.update(id, activityDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Practognostic Activity", description = "Delete a practognostic activity from the activity bank by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return activityBankService.delete(id);
    }
}

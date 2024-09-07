package com.calcpal.ideognosticdiagnosisservice.controller;

import com.calcpal.ideognosticdiagnosisservice.DTO.ActivityDTO;
import com.calcpal.ideognosticdiagnosisservice.service.IdeognosticActivitiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ideognostic/activities")
@Tag(name = "Ideognostic Activities Controllers")
@RequiredArgsConstructor
public class IdeognosticActivitiesController {
    private final IdeognosticActivitiesService activitiesService;

    @PostMapping("/")
    @Operation(summary = "Add new Activity Result", description = "Add a new activity result to the database.")
    public ResponseEntity<?> add(@Valid @RequestBody ActivityDTO activityDTO) {
        return activitiesService.add(activityDTO);
    }

    @GetMapping("/")
    @Operation(summary = "Get all Activity Results by User Email", description = "Retrieve all activity results for a specific user by email.")
    public ResponseEntity<?> getByEmail(@RequestParam String email) {
        return activitiesService.getByEmail(email);
    }

    @GetMapping("/level")
    @Operation(summary = "Get all Activity Results by User Email and Activity", description = "Retrieve all activity results for a specific user by email and activity level.")
    public ResponseEntity<?> getByEmailAndLevel(@RequestParam String email, @RequestParam String activityName) {
        return activitiesService.getByEmailAndLevel(email, activityName);
    }

    @PutMapping("/")
    @Operation(summary = "Update an Activity Result", description = "Update an existing activity result in the database.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody ActivityDTO activityDTO) {
        return activitiesService.update(id, activityDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an activity data", description = "Delete an Ideognostic Activity data from the database by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return activitiesService.delete(id);
    }
}

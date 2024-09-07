package com.calcpal.operationaldiagnosisservice.Controllers;

import com.calcpal.operationaldiagnosisservice.DTO.ActivityDTO;
import com.calcpal.operationaldiagnosisservice.Services.operationalActivitiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operational/activities")
@Tag(name = "Operational Activities Controllers")
@RequiredArgsConstructor
public class operationalActivitiesController {

    private final operationalActivitiesService activitiesService;

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
    @Operation(summary = "Get all Activity Results by User Email and Level", description = "Retrieve all activity results for a specific user by email and activity level.")
    public ResponseEntity<?> getByEmailAndLevel(@RequestParam String email, @RequestParam int level) {
        return activitiesService.getByEmailAndLevel(email, level);
    }

    @PutMapping("/")
    @Operation(summary = "Update an Activity Result", description = "Update an existing activity result in the database.")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody ActivityDTO activityDTO) {
        return activitiesService.update(id, activityDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an activity data", description = "Delete an Operational Activity data from the database by its ID.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return activitiesService.delete(id);
    }
}


package com.calcpal.ideognosticdiagnosisservice.service;

import com.calcpal.ideognosticdiagnosisservice.DTO.ActivityDTO;
import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticActivities;
import com.calcpal.ideognosticdiagnosisservice.repository.IdeognosticActivitiesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdeognosticActivitiesServiceImpl implements IdeognosticActivitiesService{

    private final IdeognosticActivitiesRepo ideognosticActivitiesRepo;

    @Override
    public ResponseEntity<?> add(ActivityDTO activityDTO) {
        // Build the operationalActivities object from the ActivityDTO
        IdeognosticActivities Activity = IdeognosticActivities.builder()
                .userEmail(activityDTO.getUserEmail())
                .level(activityDTO.getLevel())
                .date(activityDTO.getDate())
                .numberLine(activityDTO.getNumberLine())
                .fraction(activityDTO.getFraction())
                .numberCreation(activityDTO.getNumberCreation())
                .build();

        // Save the activity to the repository
        ideognosticActivitiesRepo.save(Activity);

        // Return a response indicating successful insertion
        return ResponseEntity.status(HttpStatus.CREATED).body("New Ideognostic activity result inserted successfully");
    }


    @Override
    public ResponseEntity<?> getByEmail(String email) {
        // Retrieve all operational activities associated with the given email
        List<IdeognosticActivities> activities = ideognosticActivitiesRepo.findByUserEmail(email);

        // Check if activities were found
        if (activities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities found for the provided email.");
        }

        // Return the list of activities with an OK (200) status
        return ResponseEntity.ok(activities);
    }


    @Override
    public ResponseEntity<?> getByEmailAndLevel(String email, String activityName) {
        // Retrieve all activities matching the email and activity name
        List<IdeognosticActivities> activities = ideognosticActivitiesRepo.findByUserEmailAndActivityName(email, activityName);

        // Check if activities were found
        if (activities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities found for the provided email and activity name.");
        }

        // Return the list of activities with an OK (200) status
        return ResponseEntity.ok(activities);
    }

    @Override
    public ResponseEntity<?> update(String id, ActivityDTO activityDTO) {
        Optional<IdeognosticActivities> optionalActivity = ideognosticActivitiesRepo.findById(id);

        if (optionalActivity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activity found for the provided id");
        }

        IdeognosticActivities activity = optionalActivity.get();

        // Updating the addition activity, if present
        if (activityDTO.getNumberLine() != null) {
            activity.setNumberLine(activityDTO.getNumberLine());
        }

        // Updating the subtraction activity, if present
        if (activityDTO.getFraction() != null) {
            activity.setFraction(activityDTO.getFraction());
        }

        // Updating the multiplication activity, if present
        if (activityDTO.getNumberCreation() != null) {
            activity.setNumberCreation(activityDTO.getNumberCreation());
        }

        ideognosticActivitiesRepo.save(activity);

        return ResponseEntity.ok().body("Ideognostic Activity updated successfully!");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<IdeognosticActivities> activity = ideognosticActivitiesRepo.findById(id);

        if (activity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities found for the provided ID");
        }

        ideognosticActivitiesRepo.deleteById(id);

        return ResponseEntity.ok().body("Activity data deleted successfully!");
    }
}

package com.calcpal.operationaldiagnosisservice.Services;

import com.calcpal.operationaldiagnosisservice.Collections.operationalActivities;
import com.calcpal.operationaldiagnosisservice.DTO.ActivityDTO;
import com.calcpal.operationaldiagnosisservice.Repositary.operationalActivitiesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class operationalActivitiesServiceImpl implements  operationalActivitiesService {

    private final operationalActivitiesRepo OperationalActivitiesRepo;

    @Override
    public ResponseEntity<?> add(ActivityDTO activityDTO) {
        // Build the operationalActivities object from the ActivityDTO
        operationalActivities Activity = operationalActivities.builder()
                .userEmail(activityDTO.getUserEmail())
                .level(activityDTO.getLevel())
                .date(activityDTO.getDate())
                .addition(activityDTO.getAddition())
                .subtraction(activityDTO.getSubtraction())
                .multiplication(activityDTO.getMultiplication())
                .division(activityDTO.getDivision())
                .build();

        // Save the activity to the repository
        OperationalActivitiesRepo.save(Activity);

        // Return a response indicating successful insertion
        return ResponseEntity.status(HttpStatus.CREATED).body("New Operational activity result inserted successfully");
    }


    @Override
    public ResponseEntity<?> getByEmail(String email) {
        // Retrieve all operational activities associated with the given email
        List<operationalActivities> activities = OperationalActivitiesRepo.findByUserEmail(email);

        // Check if activities were found
        if (activities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities found for the provided email.");
        }

        // Return the list of activities with an OK (200) status
        return ResponseEntity.ok(activities);
    }

    @Override
    public ResponseEntity<?> getByEmailAndLevel(String email, int level) {
        // Retrieve all operational activities associated with the given email and level
        List<operationalActivities> activities = OperationalActivitiesRepo.findByUserEmailAndLevel(email, level);

        // Check if activities were found
        if (activities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities found for the provided email and level.");
        }

        // Return the list of activities with an OK (200) status
        return ResponseEntity.ok(activities);
    }

    @Override
    public ResponseEntity<?> update(String id, ActivityDTO activityDTO) {
        Optional<operationalActivities> optionalActivity = OperationalActivitiesRepo.findById(id);

        if (optionalActivity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activity found for the provided id");
        }

        operationalActivities activity = optionalActivity.get();

        // Updating the addition activity, if present
        if (activityDTO.getAddition() != null) {
            activity.setAddition(activityDTO.getAddition());
        }

        // Updating the subtraction activity, if present
        if (activityDTO.getSubtraction() != null) {
            activity.setSubtraction(activityDTO.getSubtraction());
        }

        // Updating the multiplication activity, if present
        if (activityDTO.getMultiplication() != null) {
            activity.setMultiplication(activityDTO.getMultiplication());
        }

        // Updating the division activity, if present
        if (activityDTO.getDivision() != null) {
            activity.setDivision(activityDTO.getDivision());
        }

        OperationalActivitiesRepo.save(activity);

        return ResponseEntity.ok().body("Operational Activity updated successfully!");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<operationalActivities> activity = OperationalActivitiesRepo.findById(id);

        if (activity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities found for the provided ID");
        }

        OperationalActivitiesRepo.deleteById(id);

        return ResponseEntity.ok().body("Activity data deleted successfully!");
    }

}


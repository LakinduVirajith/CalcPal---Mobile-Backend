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
                .date(activityDTO.getDate())
                .activityName(activityDTO.getActivityName())
                .timeTaken(activityDTO.getTimeTaken())
                .totalScore(activityDTO.getTotalScore())
                .retries(activityDTO.getRetries())
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
    public ResponseEntity<?> getByEmailAndLevel(String email, String activityname) {
        // Retrieve all operational activities associated with the given email and level
        List<operationalActivities> activities = OperationalActivitiesRepo.findByUserEmailAndActivityName(email, activityname);

        // Check if activities were found
        if (activities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities found for the provided email and level.");
        }

        // Return the list of activities with an OK (200) status
        return ResponseEntity.ok(activities);
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


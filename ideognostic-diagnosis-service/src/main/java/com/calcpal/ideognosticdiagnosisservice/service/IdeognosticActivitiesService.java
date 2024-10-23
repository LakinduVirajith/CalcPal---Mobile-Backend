package com.calcpal.ideognosticdiagnosisservice.service;

import com.calcpal.ideognosticdiagnosisservice.DTO.ActivityDTO;
import org.springframework.http.ResponseEntity;

public interface IdeognosticActivitiesService {
    ResponseEntity<?> add(ActivityDTO activityDTO);

    ResponseEntity<?> getByEmail(String email);

    ResponseEntity<?> getByEmailAndLevel(String email, String activityName);

    ResponseEntity<?> delete(String id);
}

package com.calcpal.operationaldiagnosisservice.Services;

import com.calcpal.operationaldiagnosisservice.Collections.operationalActivities;
import com.calcpal.operationaldiagnosisservice.DTO.ActivityDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface operationalActivitiesService {
    ResponseEntity<?> add(ActivityDTO activityDTO);

    ResponseEntity<?> getByEmail(String email);

    ResponseEntity<?> getByEmailAndLevel(String email, int level);

    ResponseEntity<?> update(String id, ActivityDTO activityDTO);

    ResponseEntity<?> delete(String id);

}

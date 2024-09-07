package com.calcpal.operationaldiagnosisservice.Repositary;

import com.calcpal.operationaldiagnosisservice.Collections.operationalActivities;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface operationalActivitiesRepo extends MongoRepository<operationalActivities, String> {
    List<operationalActivities> findByUserEmail(String userEmail);

    List<operationalActivities> findByUserEmailAndLevel(String userEmail, int level);
}

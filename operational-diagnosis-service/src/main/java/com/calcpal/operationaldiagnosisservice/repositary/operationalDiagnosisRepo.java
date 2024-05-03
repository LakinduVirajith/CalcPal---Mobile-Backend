package com.calcpal.operationaldiagnosisservice.repositary;

import com.calcpal.operationaldiagnosisservice.collections.operationalDiagnosis;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface operationalDiagnosisRepo extends MongoRepository<operationalDiagnosis, String> {
}

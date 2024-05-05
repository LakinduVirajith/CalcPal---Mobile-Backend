package com.calcpal.operationaldiagnosisservice.Repositary;

import com.calcpal.operationaldiagnosisservice.Collections.operationalDiagnosis;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface operationalDiagnosisRepo extends MongoRepository<operationalDiagnosis, String> {
}

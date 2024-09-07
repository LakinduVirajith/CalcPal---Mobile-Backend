package com.calcpal.operationaldiagnosisservice.Repositary;

import com.calcpal.operationaldiagnosisservice.Collections.OperationalDiagnosis;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationalDiagnosisRepo extends MongoRepository<OperationalDiagnosis, String> {
}

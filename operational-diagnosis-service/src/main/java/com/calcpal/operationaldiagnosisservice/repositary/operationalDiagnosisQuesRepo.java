package com.calcpal.operationaldiagnosisservice.repositary;

import com.calcpal.operationaldiagnosisservice.collections.operationalDiagnosisQues;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface operationalDiagnosisQuesRepo extends MongoRepository<operationalDiagnosisQues, String> {
}

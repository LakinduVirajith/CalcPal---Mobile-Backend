package com.calcpal.verbaldiagnosisservice.repository;

import com.calcpal.verbaldiagnosisservice.collection.VerbalDiagnosis;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VerbalDiagnosisRepository extends MongoRepository<VerbalDiagnosis, String> {
}

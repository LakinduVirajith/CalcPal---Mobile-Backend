package com.calcpal.verbaldiagnosisservice.repository;

import com.calcpal.verbaldiagnosisservice.collection.DiagnosisResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisResultRepository extends MongoRepository<DiagnosisResult, String> {
}

package com.calcpal.visualdiagnosisservice.repository;

import com.calcpal.visualdiagnosisservice.collection.DiagnosisResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisResultRepository extends MongoRepository<DiagnosisResult, String> {
}

package com.calcpal.sequentialdiagnosisservice.repository;

import com.calcpal.sequentialdiagnosisservice.collection.DiagnosisResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisResultRepository extends MongoRepository<DiagnosisResult,String> {
}

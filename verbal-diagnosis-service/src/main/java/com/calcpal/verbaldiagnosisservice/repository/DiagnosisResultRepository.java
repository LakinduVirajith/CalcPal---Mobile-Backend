package com.calcpal.verbaldiagnosisservice.repository;

import com.calcpal.verbaldiagnosisservice.collection.DiagnosisResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiagnosisResultRepository extends MongoRepository<DiagnosisResult, String> {
}

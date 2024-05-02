package com.calcpal.lexicaldiagnosisservice.repository;

import com.calcpal.lexicaldiagnosisservice.collection.DiagnosisResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisResultRepository extends MongoRepository<DiagnosisResult, String> {
}

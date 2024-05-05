package com.calcpal.practognosticdiagnosisservice.repository;

import com.calcpal.practognosticdiagnosisservice.collection.DiagnosisResultPractognostic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PractognosticDiagnosisResultRepository  extends MongoRepository<DiagnosisResultPractognostic, String> {
}

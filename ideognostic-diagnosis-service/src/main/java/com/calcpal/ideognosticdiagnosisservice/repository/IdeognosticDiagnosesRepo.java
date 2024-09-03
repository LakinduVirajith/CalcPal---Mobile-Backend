package com.calcpal.ideognosticdiagnosisservice.repository;

import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticDiagnosis;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeognosticDiagnosesRepo extends MongoRepository<IdeognosticDiagnosis, String> {
}

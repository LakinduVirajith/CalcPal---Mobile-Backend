package com.calcpal.practognosticdiagnosisservice.repository;

import com.calcpal.practognosticdiagnosisservice.collection.DiagnosisQuestionPractognostic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PractognosticQuestionRepository extends MongoRepository<DiagnosisQuestionPractognostic, String>  {
    List<DiagnosisQuestionPractognostic> findByQuestionNumber(Long id);
}

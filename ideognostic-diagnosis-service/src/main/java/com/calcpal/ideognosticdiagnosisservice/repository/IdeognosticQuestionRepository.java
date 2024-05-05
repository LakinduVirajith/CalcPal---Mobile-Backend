package com.calcpal.ideognosticdiagnosisservice.repository;

import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeognosticQuestionRepository extends MongoRepository<IdeognosticQuestion, String> {
    List<IdeognosticQuestion> findByQuestionNumber(Long id);
}

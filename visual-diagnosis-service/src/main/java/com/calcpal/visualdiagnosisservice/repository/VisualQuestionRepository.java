package com.calcpal.visualdiagnosisservice.repository;

import com.calcpal.visualdiagnosisservice.collection.VisualQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisualQuestionRepository extends MongoRepository<VisualQuestion, String> {
    List<VisualQuestion> findByQuestionNumber(Long id);
}

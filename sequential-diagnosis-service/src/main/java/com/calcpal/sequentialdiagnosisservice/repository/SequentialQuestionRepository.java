package com.calcpal.sequentialdiagnosisservice.repository;

import com.calcpal.sequentialdiagnosisservice.collection.SequentialQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SequentialQuestionRepository  extends MongoRepository<SequentialQuestion, String> {
    List<SequentialQuestion> findByQuestionNumber(Long id);
}

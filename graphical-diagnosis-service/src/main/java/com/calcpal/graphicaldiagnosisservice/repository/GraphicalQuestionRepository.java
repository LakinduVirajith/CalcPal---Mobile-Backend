package com.calcpal.graphicaldiagnosisservice.repository;


import com.calcpal.graphicaldiagnosisservice.collection.GraphicalQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphicalQuestionRepository extends MongoRepository<GraphicalQuestion, String> {
    List<GraphicalQuestion> findByQuestionNumber(Long id);
}

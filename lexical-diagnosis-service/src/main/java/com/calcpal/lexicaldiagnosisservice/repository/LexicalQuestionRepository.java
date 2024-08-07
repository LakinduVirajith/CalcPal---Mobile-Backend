package com.calcpal.lexicaldiagnosisservice.repository;

import com.calcpal.lexicaldiagnosisservice.collection.LexicalQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LexicalQuestionRepository extends MongoRepository<LexicalQuestion, String> {
    List<LexicalQuestion> findByQuestionNumber(Long id);
}

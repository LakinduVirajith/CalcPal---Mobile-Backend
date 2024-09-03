package com.calcpal.lexicaldiagnosisservice.repository;

import com.calcpal.lexicaldiagnosisservice.collection.LexicalActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LexicalActivityRepository extends MongoRepository<LexicalActivity, String> {
    List<LexicalActivity> findByActivityNumber(Long id);
}

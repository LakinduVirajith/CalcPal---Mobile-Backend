package com.calcpal.verbaldiagnosisservice.repository;

import com.calcpal.verbaldiagnosisservice.collection.VerbalQuestion;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerbalQuestionRepository extends MongoRepository<VerbalQuestion, String> {
    List<VerbalQuestion> findByQuestionNumber(Long id);
}

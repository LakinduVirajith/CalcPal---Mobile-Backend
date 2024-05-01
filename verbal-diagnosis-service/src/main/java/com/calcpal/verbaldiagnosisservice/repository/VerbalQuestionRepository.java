package com.calcpal.verbaldiagnosisservice.repository;

import com.calcpal.verbaldiagnosisservice.collection.VerbalQuestion;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VerbalQuestionRepository extends MongoRepository<VerbalQuestion, ObjectId> {
    List<VerbalQuestion> findByQuestionNumber(Long id);
}

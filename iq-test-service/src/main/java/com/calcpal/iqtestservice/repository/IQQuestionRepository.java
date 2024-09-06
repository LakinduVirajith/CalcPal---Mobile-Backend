package com.calcpal.iqtestservice.repository;

import com.calcpal.iqtestservice.collection.IQQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQQuestionRepository extends MongoRepository<IQQuestion, String> {
    List<IQQuestion> findByQuestionNumber(Long id);
}

package com.calcpal.verbaldiagnosisservice.repository;

import com.calcpal.verbaldiagnosisservice.collection.VerbalActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerbalActivityRepository extends MongoRepository<VerbalActivity, String> {
    List<VerbalActivity> findByActivityNumber(Long id);
}

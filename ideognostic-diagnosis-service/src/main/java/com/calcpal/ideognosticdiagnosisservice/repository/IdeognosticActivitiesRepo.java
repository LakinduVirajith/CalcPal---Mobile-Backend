package com.calcpal.ideognosticdiagnosisservice.repository;

import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticActivities;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeognosticActivitiesRepo extends MongoRepository<IdeognosticActivities, String>{
    List<IdeognosticActivities> findByUserEmail(String userEmail);

    @Query("{ 'userEmail': ?0, '$or': [ {'numberLine.name': ?1}, {'fraction.name': ?1}, {'numberCreation.name': ?1} ] }")
    List<IdeognosticActivities> findByUserEmailAndActivityName(String email, String activityName);
}

package com.calcpal.ideognosticdiagnosisservice.repository;

import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticActivities;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeognosticActivitiesRepo extends MongoRepository<IdeognosticActivities, String>{
    List<IdeognosticActivities> findByUserEmail(String userEmail);

    List<IdeognosticActivities> findByUserEmailAndActivityName(String userEmail, String activityName);
}

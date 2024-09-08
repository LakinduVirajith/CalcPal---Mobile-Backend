package com.calcpal.practognosticdiagnosisservice.repository;

import com.calcpal.practognosticdiagnosisservice.collection.PractognosticActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PractognosticActivityRepository extends MongoRepository<PractognosticActivity, String> {
    List<PractognosticActivity> findByActivityNumber(Long id);
}

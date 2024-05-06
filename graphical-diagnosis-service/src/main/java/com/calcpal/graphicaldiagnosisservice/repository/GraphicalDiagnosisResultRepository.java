package com.calcpal.graphicaldiagnosisservice.repository;

import com.calcpal.graphicaldiagnosisservice.collection.DiagnosisResultGraphical;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphicalDiagnosisResultRepository extends MongoRepository<DiagnosisResultGraphical, String> {
}

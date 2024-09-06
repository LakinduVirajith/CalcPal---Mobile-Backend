package com.calcpal.operationaldiagnosisservice.Repositary;

import com.calcpal.operationaldiagnosisservice.Collections.OperationalDiagnosisQues;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationalDiagnosisQuesRepo extends MongoRepository<OperationalDiagnosisQues, String> {
    List<OperationalDiagnosisQues> findByQuestionNumber(Long id);
}

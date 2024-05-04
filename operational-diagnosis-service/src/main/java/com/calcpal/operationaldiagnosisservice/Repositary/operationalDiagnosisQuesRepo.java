package com.calcpal.operationaldiagnosisservice.Repositary;

import com.calcpal.operationaldiagnosisservice.Collections.operationalDiagnosis;
import com.calcpal.operationaldiagnosisservice.Collections.operationalDiagnosisQues;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface operationalDiagnosisQuesRepo extends MongoRepository<operationalDiagnosisQues, String> {
    List<operationalDiagnosisQues> findByQuestionNumber(Long id);
}

package com.calcpal.operationaldiagnosisservice.Services;

import com.calcpal.operationaldiagnosisservice.Collections.OperationalDiagnosis;

public interface EmailService {

    boolean sendDiagnosisResultMail(OperationalDiagnosis result);
}

package com.calcpal.graphicaldiagnosisservice.service;

import com.calcpal.graphicaldiagnosisservice.collection.DiagnosisResultGraphical;

public interface EmailService {

    boolean sendDiagnosisResultMail(DiagnosisResultGraphical result);
}

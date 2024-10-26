package com.calcpal.ideognosticdiagnosisservice.service;

import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticDiagnosis;

public interface EmailService {

    boolean sendDiagnosisResultMail(IdeognosticDiagnosis result);
}

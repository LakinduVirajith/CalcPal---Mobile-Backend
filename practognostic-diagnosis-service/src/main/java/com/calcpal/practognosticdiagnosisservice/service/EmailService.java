package com.calcpal.practognosticdiagnosisservice.service;

import com.calcpal.practognosticdiagnosisservice.collection.DiagnosisResultPractognostic;

public interface EmailService {

    boolean sendDiagnosisResultMail(DiagnosisResultPractognostic result);
}

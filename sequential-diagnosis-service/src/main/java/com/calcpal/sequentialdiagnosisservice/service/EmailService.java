package com.calcpal.sequentialdiagnosisservice.service;

import com.calcpal.sequentialdiagnosisservice.collection.DiagnosisResult;

public interface EmailService {

    boolean sendDiagnosisResultMail(DiagnosisResult result);
}

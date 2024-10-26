package com.calcpal.lexicaldiagnosisservice.service;

import com.calcpal.lexicaldiagnosisservice.collection.DiagnosisResult;

public interface EmailService {

    boolean sendDiagnosisResultMail(DiagnosisResult result);
}

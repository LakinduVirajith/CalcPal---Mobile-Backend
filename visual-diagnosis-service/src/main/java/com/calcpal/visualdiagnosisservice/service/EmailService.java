package com.calcpal.visualdiagnosisservice.service;

import com.calcpal.visualdiagnosisservice.collection.DiagnosisResult;

public interface EmailService {

    boolean sendDiagnosisResultMail(DiagnosisResult result);
}

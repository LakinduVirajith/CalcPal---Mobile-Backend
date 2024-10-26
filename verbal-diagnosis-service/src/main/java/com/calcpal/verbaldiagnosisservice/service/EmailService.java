package com.calcpal.verbaldiagnosisservice.service;

import com.calcpal.verbaldiagnosisservice.collection.DiagnosisResult;

public interface EmailService {

    boolean sendDiagnosisResultMail(DiagnosisResult result);
}

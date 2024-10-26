package com.calcpal.practognosticdiagnosisservice.service;

import com.calcpal.practognosticdiagnosisservice.collection.DiagnosisResultPractognostic;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${mail.username}")
    private String fromMail;

    @Override
    public boolean sendDiagnosisResultMail(DiagnosisResultPractognostic result) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(fromMail);
            messageHelper.setTo(result.getUserEmail());
            messageHelper.setSubject("Quiz Results");

            // Load HTML template
            String htmlTemplate = Files.readString(new ClassPathResource("templates/diagnosisResult.html").getFile().toPath(), StandardCharsets.UTF_8);

            // Replace placeholders with actual values
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("${q1}", result.getQ1() ? "Correct" : "Incorrect");
            placeholders.put("${q2}", result.getQ2() ? "Correct" : "Incorrect");
            placeholders.put("${q3}", result.getQ3() ? "Correct" : "Incorrect");
            placeholders.put("${q4}", result.getQ4() ? "Correct" : "Incorrect");
            placeholders.put("${q5}", result.getQ5() ? "Correct" : "Incorrect");
            placeholders.put("${totalScore}", String.valueOf(result.getTotalScore()));
            placeholders.put("${diagnosisSummary}", result.getLabel() != null
                    ? (result.getLabel() ? "<span style='color: #e74c3c;'>At Risk</span>" : "No Risk Detected")
                    : "");

            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                htmlTemplate = htmlTemplate.replace(entry.getKey(), entry.getValue());
            }

            messageHelper.setText(htmlTemplate, true);

            mailSender.send(mimeMessage);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

package com.calcpal.lexicaldiagnosisservice.service;

import com.calcpal.lexicaldiagnosisservice.collection.DiagnosisResult;
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
    public boolean sendDiagnosisResultMail(DiagnosisResult result) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(fromMail);
            messageHelper.setTo(result.getUserEmail());
            messageHelper.setSubject("Quiz Results");

            // Embedded HTML template
            String htmlTemplate = """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>Quiz Results</title>
                    </head>
                        <body style="font-family: Arial, sans-serif; color: #333;">
                            <h2>Your Child's Quiz Results</h2>
                            <p>We have completed the assessment for your child. Below is a summary of their responses:</p>
                            <table style="border-collapse: collapse; width: 40%;">
                                <tr><th style="border: 1px solid #ddd; padding: 8px;">Question</th><th style="border: 1px solid #ddd; padding: 8px;">Response</th></tr>
                                <tr><td style="border: 1px solid #ddd; padding: 8px;">Question 1</td><td style="border: 1px solid #ddd; padding: 8px;">${q1}</td></tr>
                                <tr><td style="border: 1px solid #ddd; padding: 8px;">Question 2</td><td style="border: 1px solid #ddd; padding: 8px;">${q2}</td></tr>
                                <tr><td style="border: 1px solid #ddd; padding: 8px;">Question 3</td><td style="border: 1px solid #ddd; padding: 8px;">${q3}</td></tr>
                                <tr><td style="border: 1px solid #ddd; padding: 8px;">Question 4</td><td style="border: 1px solid #ddd; padding: 8px;">${q4}</td></tr>
                                <tr><td style="border: 1px solid #ddd; padding: 8px;">Question 5</td><td style="border: 1px solid #ddd; padding: 8px;">${q5}</td></tr>
                            </table>
                            <p><strong>Total Score:</strong> ${totalScore}</p>
                            <p><strong>Diagnosis Summary:</strong> ${diagnosisSummary} in Lexical</p>
                            <p>This assessment provides a summary of areas where your child may need additional support. If you have any concerns, please consider reaching out to an educational specialist.</p>
                            <p>Place mae sure the child attempts the relevant activities assigned to him/her in the app. You will be able to navigate to them when u login to the app.</p>
                            <p>Thank you for your attention and support.</p>
                            <p>Sincerely,<br>CalcPal Team</p>
                        </body>
                    </html>
                """;

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

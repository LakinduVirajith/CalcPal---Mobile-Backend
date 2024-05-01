package com.calcapl.userservice.service;

import com.calcapl.userservice.collection.User;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Value(("${base.url}"))
    private String baseUrl;

    @Override
    public boolean sendActivationMail(User user) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(fromMail);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Activate Your Account");

            String activationUrl = baseUrl + "/api/v1/user/activate?token=" + user.getActivationToken().getToken();

            String emailBody = "<html>"
                    + "<body style='font-family: Roboto, Arial, sans-serif; width: fit-content;'>"
                    + "<h3 style='color: #252525;'>Hello " + user.getName() + "</h3>"
                    + "<hr style='height: 1px; width: 100%; background-color: #252525;' />"
                    + "<p style='color: #252525;'>Thank you for registering with CalcPal.</p>"
                    + "<p style='margin-bottom: 24px; color: #252525;'>Activate your account, please click the <strong style='color: #252525;'>Verify Email</strong> button below:</p>"
                    + "<button style='background-color: white; border: solid 2px #252525; border-radius: 8px; padding: 8px; cursor: pointer;'>"
                    + "<a style='color: inherit; text-decoration: none; font-size: 14px;' href='" + activationUrl + "'>Verify Email</a>"
                    + "</button>"
                    + "<p style='margin-top: 24px; color: #252525;'>This activation link will expire in 10 minutes for security reasons.</p>"
                    + "<p style='margin-bottom: 24; color: #252525;'>so make sure to activate your account promptly.</p>"
                    + "<p style='color: #252525;'>If you didn't sign up for CalcPal, you can safely ignore this email.</p>"
                    + "<hr style='height: 1px;  margin-top: 24px; width: 100%; background-color: #252525;' />"
                    + "<p style='color: #252525;'>Best regards,</p>"
                    + "<p style='color: #252525;'>CalcPal Team</p>"
                    + "</body>"
                    + "</html>";

            messageHelper.setText(emailBody, true);

            mailSender.send(mimeMessage);

            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

package com.calcpal.practognosticdiagnosisservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    @Value("${mail.username}")
    private String userName;

    @Value("${mail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(userName);
        mailSender.setPassword(password);

        // ADDITIONAL MAIL PROPERTIES
         mailSender.setJavaMailProperties(javaMailProperties());

        return mailSender;
    }

    // SET ADDITIONAL MAIL PROPERTIES
    private Properties javaMailProperties() {
         Properties properties = new Properties();
         properties.setProperty("mail.smtp.auth", "true");
         properties.setProperty("mail.smtp.starttls.enable", "true");
         return properties;
     }
}

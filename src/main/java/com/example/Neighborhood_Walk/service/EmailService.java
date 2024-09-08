package com.example.Neighborhood_Walk.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Communea@outlook.com");
        message.setTo(toEmail);
        message.setSubject("Email Verification - Communea");
        message.setText("Thanks for registration. Please use the following code to finish e-mail verification: " + token);
        mailSender.send(message);
    }
}


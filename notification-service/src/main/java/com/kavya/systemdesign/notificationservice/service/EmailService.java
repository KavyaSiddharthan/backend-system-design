package com.kavya.systemdesign.notificationservice.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;

    @Async
    public void sendEmail(String to, String subject, String body) {
        try {
            log.info("Preparing to send email to {}", to);
            // Simulating connection delay
            Thread.sleep(2000); 
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@kavya-system-design.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            
            emailSender.send(message);
            log.info("Email successfully sent to {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to {}", to, e);
        }
    }
}

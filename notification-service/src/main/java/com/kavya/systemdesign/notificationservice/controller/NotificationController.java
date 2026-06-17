package com.kavya.systemdesign.notificationservice.controller;
import com.kavya.systemdesign.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<String> sendEmailNotification(@RequestParam String to, @RequestParam String subject, @RequestBody String body) {
        // Trigger Async email send
        emailService.sendEmail(to, subject, body);
        return ResponseEntity.accepted().body("Email request received and queued for sending.");
    }
}

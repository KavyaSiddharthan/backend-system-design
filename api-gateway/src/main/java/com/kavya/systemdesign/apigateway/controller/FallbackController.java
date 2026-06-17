package com.kavya.systemdesign.apigateway.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/rate-limiter")
    public ResponseEntity<String> rateLimiterFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("The Rate Limiter Service is currently unavailable. This is a Circuit Breaker fallback response.");
    }
}

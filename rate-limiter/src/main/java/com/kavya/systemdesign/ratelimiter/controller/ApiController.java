package com.kavya.systemdesign.ratelimiter.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
    @GetMapping("/free")
    public String free() {
        return "This is a free endpoint. No limits.";
    }

    @GetMapping("/limited")
    public String limited() {
        return "You successfully accessed the limited endpoint!";
    }
}

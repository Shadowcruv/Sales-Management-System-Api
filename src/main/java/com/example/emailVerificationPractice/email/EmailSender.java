package com.example.emailVerificationPractice.email;

import org.springframework.context.annotation.Configuration;

@Configuration
public interface EmailSender {

    void send(String to, String email);
}

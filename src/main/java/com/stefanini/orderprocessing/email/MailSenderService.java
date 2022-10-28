package com.stefanini.orderprocessing.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class MailSenderService {
    private final Environment environment;
    @Autowired
    private JavaMailSender mailSender;

    public MailSenderService(Environment environment, JavaMailSenderImpl mailSender) {
        this.environment = environment;
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String email, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Objects.requireNonNull(environment.getProperty("spring.mail.username")));
        message.setTo(to);
        message.setText(email);
        message.setSubject(subject);


        mailSender.send(message);
    }

    public void sendMail( String email, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Objects.requireNonNull(environment.getProperty("spring.mail.username")));
        message.setTo(environment.getProperty("spring.mail.username"));
        message.setText(email);
        message.setSubject(subject);


        mailSender.send(message);
    }

}

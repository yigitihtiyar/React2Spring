package com.example.ws.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import com.example.ws.configuration.HoaxifyProperties;
import jakarta.annotation.PostConstruct;



@Service
public class EmailService {
    
  JavaMailSenderImpl mailSender;

  @Autowired
  HoaxifyProperties hoaxifyProperties;

  @PostConstruct
  public void initilaze() {
    this.mailSender = new JavaMailSenderImpl();
    mailSender.setHost(hoaxifyProperties.getEmail().host());
    mailSender.setPort(hoaxifyProperties.getEmail().port());
    mailSender.setUsername(hoaxifyProperties.getEmail().username());
    mailSender.setPassword(hoaxifyProperties.getEmail().password());
    Properties properties = mailSender.getJavaMailProperties();
    properties.put("mail.stmp.starttls.enable", "true");
  }


    public void sendActivationEmail(String email , String activationToken) {
    var activationUrl = hoaxifyProperties.getClient().host() + "/activation" + activationToken;
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(hoaxifyProperties.getEmail().from());
    message.setTo(email);
    message.setSubject("Account Activation");
    message.setText(activationUrl);
    this.mailSender.send(message);
  }
}

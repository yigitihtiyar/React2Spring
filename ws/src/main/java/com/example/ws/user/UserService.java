package com.example.ws.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ws.email.EmailService;
import com.example.ws.user.exception.ActivationNotificationException;
import com.example.ws.user.exception.InavalidExceptionToken;
import com.example.ws.user.exception.NotUniqueEmailException;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  @Autowired // DI
  UserRepository userRepository;

  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();// password hashing

  @Autowired
  EmailService emailService;

  @Transactional(rollbackOn = MailException.class)
  public void save(User user) {
    try {
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setActivationToken(UUID.randomUUID().toString());
      user.setPassword(encodedPassword);
      userRepository.saveAndFlush(user);
      emailService.sendActivationEmail(user.getEmail(), user.activationToken);
    } catch (DataIntegrityViolationException ex) {
      throw new NotUniqueEmailException();
    } catch (MailException ex) {
      throw new ActivationNotificationException();
    }

  }

  public void activateUser(String token) {
    User user = userRepository.findByActivationToken(token);
    if (user == null) {
      throw new InavalidExceptionToken();
    }
    user.setActive(true);
    user.setActivationToken(null);
    userRepository.save(user);
  }

  public List<User> getUsers() {

    return userRepository.findAll();
  }

}

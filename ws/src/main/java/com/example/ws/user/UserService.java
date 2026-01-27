package com.example.ws.user;


import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ws.configuration.CurrentUser;
import com.example.ws.email.EmailService;
import com.example.ws.user.dto.UserUpdate;
import com.example.ws.user.exception.ActivationNotificationException;
import com.example.ws.user.exception.InvalidTokenException;
import com.example.ws.user.exception.NotFoundException;
import com.example.ws.user.exception.NotUniqueEmailException;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  @Autowired 
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

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
      throw new InvalidTokenException();
    }
    user.setActive(true);
    user.setActivationToken(null);
    userRepository.save(user);
  }

  public Page<User> getUsers(Pageable page,CurrentUser currentUser) {
    if(currentUser == null)
      {
         return userRepository.findAll(page);
      }

      return userRepository.findByIdNot(currentUser.getId(),page);
   
  }

  public User getUser(long id) {
    return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
 
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User updateUser(long id, UserUpdate userUpdate) {
   
      User inDB = getUser(id);
      inDB.setUsername(userUpdate.username());
      inDB.setImage(userUpdate.image());
      return userRepository.save(inDB);
   
  }

}

package com.example.ws.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.example.ws.configuration.HoaxifyProperties;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

  JavaMailSenderImpl mailSender;

  @Autowired
  HoaxifyProperties hoaxifyProperties;

  @Autowired
  MessageSource messageSource;

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

  String activationEmail = """
      <html>
          <body>
              <h1>${title}</h1>
              <a href="%s"${url}>${clikHere}</a>
          </body>
      </html>
      """;

  public void sendActivationEmail(String email, String activationToken) {
    var activationUrl = hoaxifyProperties.getClient().host() + "/activation" + activationToken;

    var title = messageSource.getMessage("hoaxify.mail.user.created.title", null, LocaleContextHolder.getLocale());
    var clikHere = messageSource.getMessage("hoaxify.mail.click.here", null, LocaleContextHolder.getLocale());

    var mailBody = activationEmail
        .replace("${url}", activationUrl)
        .replace("${title}", title)
        .replace("${clickHere}", clikHere);

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
    try {
      message.setFrom(hoaxifyProperties.getEmail().from());
      message.setTo(email);
      message.setSubject(title);
      message.setText(mailBody, true);

    } catch (MessagingException e) {

      e.printStackTrace();
    }
    this.mailSender.send(mimeMessage);
  }
}

package com.qucat.quiz.services;

import com.qucat.quiz.repositories.entities.Lang;
import com.qucat.quiz.repositories.entities.MessageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
@PropertySource("classpath:mail/application-mail-config.properties")
public class EmailSender {

    @Value("${login}")
    private String login;

    private final String PATH = "Quiz-server/src/main/resources/mail/";

    @Autowired
    private Session emailSession;


    public void sendPasswordRecoveryMessage(String receiverEmailAddress, String username, String url, Lang lang) {
        try {
            Message message = generateMessage(receiverEmailAddress);
            setContent(message, username, url,lang.getPasswordRecovery());
            Transport.send(message);
        } catch (MessagingException | IOException e) {
            log.error("cant send reset password message", e);
        }
    }

    public void sendRegistrationMessage(String receiverEmailAddress, String username, String url, Lang lang) {
        try {
            Message message = generateMessage(receiverEmailAddress);
            setContent(message, username, url, lang.getRegistration());
            Transport.send(message);
        } catch (MessagingException | IOException e) {
            log.error("cant send registration message", e);
        }
    }

    private Message generateMessage(String receiverEmailAddress) throws MessagingException {
        Message message = new MimeMessage(emailSession);
        message.setFrom(new InternetAddress(login));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(receiverEmailAddress)
        );
        return message;
    }

    private void setContent(Message message, String userName, String url, MessageInfo messageInfo) throws MessagingException, IOException {
        message.setSubject( messageInfo.getSubject()+ " Qucat");
        String content = new String(Files.readAllBytes(Paths.get(PATH+messageInfo.getFilename())));
        content = content.replaceFirst("&\\{url}", url);
        content = content.replaceFirst("&\\{userName}", userName);
        message.setContent(content, "text/html ; charset=utf-8");
    }

}

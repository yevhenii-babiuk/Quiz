package com.qucat.quiz.services;

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
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;


@Slf4j
@Service
@PropertySource("classpath:mail/application-mail-config.properties")
public class EmailSender {

    @Value("${login}")
    private String login;

    private final String USERNAME_TOKEN = "&\\{userName}";
    private final String URL_TOKEN = "&\\{url}";
    private final String CONTENT_ENCODING = "text/html ; charset=utf-8";

    @Autowired
    private Session emailSession;

    public void sendMessage(String receiverEmailAddress, String username, String url, MessageInfo.MessageInfoItem messageInfoItem) {
        try {
            Message message = generateMessage(receiverEmailAddress);
            Map<String, String> replace = new TreeMap<>();
            replace.put(USERNAME_TOKEN, username);
            replace.put(URL_TOKEN, url);
            setContent(message, messageInfoItem, replace);
            Transport.send(message);
        } catch (MessagingException | IOException e) {
            log.error("cant send reset password message", e);
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

    private void setContent(Message message, MessageInfo.MessageInfoItem messageInfoItem, Map<String, String> replace) throws MessagingException, IOException {
        message.setSubject(messageInfoItem.getSubject());
        String content = new String(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(messageInfoItem.getFilename())).readAllBytes());
        for (Map.Entry<String, String> entry : replace.entrySet()) {
            content = content.replaceFirst(entry.getKey(), entry.getValue());
        }
        message.setContent(content, CONTENT_ENCODING);
    }

}

package com.qucat.quiz.services;

import com.qucat.quiz.repositories.entities.Lang;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.util.Properties;

@Service
@PropertySource("classpath:application-mail-config.properties")
public class EmailSender {

    @Value("${login}")
    private String login;

    @Value("${password}")
    private String password;
    private final Session session;

    private final String[][] REGISTRATION_INPUT=new String[2][];
    private final String[][] RESET_PASSWORD_INPUT = new String[2][];


    public EmailSender() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password);
                    }
                });

        try {
            FileInputStream fis = new FileInputStream("Quiz-server/src/main/resources/mailInput.xml");
            Properties messageInput  = new Properties();
            messageInput.loadFromXML(fis);
            REGISTRATION_INPUT[0]=messageInput.getProperty("ukRegisterInput").split(";");
            REGISTRATION_INPUT[1]= messageInput.getProperty("enRegisterInput").split(";");
            RESET_PASSWORD_INPUT[0]=messageInput.getProperty("ukResetPasswordInput").split(";");
            RESET_PASSWORD_INPUT[1]= messageInput.getProperty("enResetPasswordInput").split(";");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendResetPasswordMessage(String receiverEmailAddress, String username, String url, Lang lang) {
        try {
            Message message = generateMessage(receiverEmailAddress);
            setContent(message, username, url, RESET_PASSWORD_INPUT[lang.ordinal()]);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendRegistrationMessage(String receiverEmailAddress, String username, String url, Lang lang) {
        try {
            Message message = generateMessage(receiverEmailAddress);
            setContent(message, username, url, REGISTRATION_INPUT[lang.ordinal()]);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Message generateMessage(String receiverEmailAddress) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(login));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(receiverEmailAddress)
        );
        return message;
    }

    private void setContent(Message message, String userName, String url, String[] args) throws MessagingException {
        message.setSubject(args[0] + " Qucat");
        message.setContent("<div style='text-align: center; width: 700px'><h2>" + args[1] + ", " + userName + "!</h2>" +
                args[2] + "<br><br>" +
                "<a href='" + url + "' style='text-decoration:none'><div style='border:none;background:#0f0;padding:10px;display:block;font-size:20px;border-radius:360px;text-decoration:none'>" +
                args[3] + "</div></a> " +
                "<h6>" + args[4] + "</h6><div/>", "text/html ; charset=utf-8");
    }


}

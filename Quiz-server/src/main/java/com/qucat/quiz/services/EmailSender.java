package com.qucat.quiz.services;
import com.qucat.quiz.repositories.entities.Lang;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailSender {
    private final String LOGIN = "qucatsender@gmail.com";
    private final String PASSWORD = "tfvupauipqoymocc";
    private Session session;

    private final String[][] REGISTRATION_MESSAGE_CONSTANTS = {
            {"Підтвердження реєстрації на", "Добрий день",
                    "Підтвердьте свою адресу електронної пошти. Це допоможе відновити акаунт у разі втрати паролю",
                    "Підтвердьте адресу", "Якщо ви не очікували цього листа або отримали його помилково, проігноруйте його"},
            {"Confirm registration on", "Hi",
                    "Please confirm your email address. This will help to recover your account in case of password loss",
                    "Confirm the mail", "If you didn't expect this email or received it in error, please ignore it"}};
    private final String[][] RESET_PASSWORD_MESSAGE_CONSTANTS = {
            {"Скидання паролю ", "Добрий день",
                    "Для того щоб скинути пароль, натисніть на кнопку знизу",
                    "Скинути пароль", "Якщо ви не очікували цього листа або отримали його помилково, проігноруйте його"},
            {"Confirm reset password", "Hi",
                    "To reset your password, click the button below",
                    "Reset password", "If you didn't expect this email or received it in error, please ignore it"}};


    public EmailSender() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(LOGIN, PASSWORD);
                    }
                });
    }

    public void sendResetPasswordMessage(String receiverEmailAddress, String username, String url, Lang lang) {
        try {
            Message message = generateMessage(receiverEmailAddress);
            setContent(message, username, url, RESET_PASSWORD_MESSAGE_CONSTANTS[lang.ordinal()]);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendRegistrationMessage(String receiverEmailAddress, String username, String url, Lang lang) {
        try {
            Message message = generateMessage(receiverEmailAddress);
            setContent(message, username, url, REGISTRATION_MESSAGE_CONSTANTS[lang.ordinal()]);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Message generateMessage(String receiverEmailAddress) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(LOGIN));
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

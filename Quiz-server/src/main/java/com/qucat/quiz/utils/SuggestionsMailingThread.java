package com.qucat.quiz.utils;

import com.qucat.quiz.repositories.entities.MessageInfo;
import com.qucat.quiz.services.EmailSender;

public class SuggestionsMailingThread implements Runnable {
    private EmailSender emailSender;
    private String login;
    private String email;
    private String URL;
    private String quizName;
    private String categoryName;
    private String quizId;
    private MessageInfo.MessageInfoItem messageInfoItem;
    private final String QUIZ = "quiz/";

    public SuggestionsMailingThread(EmailSender emailSender, String login, String email, String url, String quizName, String categoryName, String quizId, MessageInfo.MessageInfoItem messageInfoItem) {
        this.emailSender = emailSender;
        this.login = login;
        this.email = email;
        this.URL = url;
        this.quizName = quizName;
        this.categoryName = categoryName;
        this.quizId = quizId;
        this.messageInfoItem = messageInfoItem;
    }

    @Override
    public void run() {
        emailSender.sendMessage(email, login, URL + QUIZ + quizId, quizName, categoryName, messageInfoItem);
    }

}

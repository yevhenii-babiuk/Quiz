package com.qucat.quiz.utils;

import com.qucat.quiz.repositories.entities.enums.MessageInfo;
import com.qucat.quiz.services.EmailSender;
import lombok.Builder;

@Builder
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


    @Override
    public void run() {
        emailSender.sendMessage(email, login, URL + QUIZ + quizId, quizName, categoryName, messageInfoItem);
    }

}

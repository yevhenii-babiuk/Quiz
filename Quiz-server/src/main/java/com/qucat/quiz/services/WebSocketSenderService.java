package com.qucat.quiz.services;

import com.google.gson.Gson;
import com.qucat.quiz.repositories.dto.quizplay.Users;
import com.qucat.quiz.repositories.entities.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebSocketSenderService {

    @Autowired
    private SimpMessagingTemplate template;

    public void sendUsers(Users users, String gameId) {
        log.info("send users");
        Gson g = new Gson();
        this.template.convertAndSend(String.format("/game/%s/play/results", gameId), g.toJson(users));
    }

    public void sendQuestion(Question question, String gameId) {
        log.info("send question");
        Gson g = new Gson();
        this.template.convertAndSend(String.format("/game/%s/play/question", gameId), g.toJson(question));

    }


    private void sendResults(String gameId, Users users) {
        System.out.println("send results");
        Gson g = new Gson();
        this.template.convertAndSend(String.format("/game/%s/play/results", gameId), g.toJson(users));
    }


}

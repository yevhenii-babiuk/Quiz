package com.qucat.quiz.services;

import com.google.gson.Gson;
import com.qucat.quiz.repositories.dto.UserDto;
import com.qucat.quiz.repositories.dto.Users;
import com.qucat.quiz.repositories.entities.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONStringer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebSocketSenderService {

    @Autowired
    private SimpMessagingTemplate template;

    public void sendResults(String gameId,Users users) {
        log.info("send results");
        Gson g = new Gson();
        this.template.convertAndSend(String.format("/game/%s/play/results", gameId), g.toJson(users));
    }

    public void sendQuestion(Question question, String gameId) {
        log.info("send question");
        Gson g = new Gson();
        this.template.convertAndSend(String.format("/game/%s/play/question", gameId), g.toJson(question));

    }

    public void sendUsers(String gameId, Users users) {
        JSONStringer stringer = new JSONStringer();
        try {
            stringer.array();
            for (UserDto user : users.getUsers()) {
                stringer.value(user.getLogin());
            }
            stringer.endArray();
            this.template.convertAndSend(String.format("/game/%s/players", gameId), stringer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

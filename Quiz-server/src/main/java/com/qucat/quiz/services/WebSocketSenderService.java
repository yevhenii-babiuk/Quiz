package com.qucat.quiz.services;

import com.google.gson.Gson;
import com.qucat.quiz.repositories.dto.UserDto;
import com.qucat.quiz.repositories.dto.Users;
import com.qucat.quiz.repositories.dto.WebsocketEvent;
import com.qucat.quiz.repositories.entities.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WebSocketSenderService {

    @Autowired
    private SimpMessagingTemplate template;

    private Gson gson = new Gson();


    public void sendResults(String gameId, Users users) {
        log.info("send results");
        this.template.convertAndSend(String.format("/game/%s/play", gameId),
                gson.toJson(WebsocketEvent.builder().type(WebsocketEvent.EventType.RESULTS).results(users).build()));
    }

    public void sendQuestion(Question question, String gameId) {
        log.info("send question");
        Gson g = new Gson();
        this.template.convertAndSend(String.format("/game/%s/play", gameId),
                g.toJson(WebsocketEvent.builder().type(WebsocketEvent.EventType.QUESTION).question(question).build()));
    }

    public void sendUsers(String gameId, Users users) {
     /*   JSONStringer stringer = new JSONStringer();
        try {
            stringer.array();
            for (UserDto user : users.getUsers()) {
                stringer.value(user.getLogin());
            }
            stringer.endArray();*/
        List<String> players = new ArrayList<>();
        for (UserDto user : users.getUsers()) {
            players.add(user.getLogin());
        }
        this.template.convertAndSend(String.format("/game/%s/play", gameId),
                gson.toJson(WebsocketEvent.builder().type(WebsocketEvent.EventType.PLAYERS).players(players).build()));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }


}

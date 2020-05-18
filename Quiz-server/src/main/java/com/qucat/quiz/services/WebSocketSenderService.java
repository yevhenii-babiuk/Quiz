package com.qucat.quiz.services;

import com.google.gson.Gson;
import com.qucat.quiz.repositories.dto.game.UserDto;
import com.qucat.quiz.repositories.dto.game.Users;
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

    private final Gson gson = new Gson();
    @Autowired
    private SimpMessagingTemplate template;

    public void sendResults(String gameId, Users users) {
        log.info("send results " + users);
        this.template.convertAndSend(String.format("/game/%s/play", gameId),
                gson.toJson(WebsocketEvent.builder().type(WebsocketEvent.EventType.RESULTS).gameResults(users).build()));
    }

    public void sendQuestion(Question question, String gameId) {
        log.info("send question");
        Gson g = new Gson();
        this.template.convertAndSend(String.format("/game/%s/play", gameId),
                g.toJson(WebsocketEvent.builder().type(WebsocketEvent.EventType.QUESTION).question(question).build()));
    }

    public void sendUsers(String gameId, List<UserDto> users) {
        List<String> players = new ArrayList<>();
        for (UserDto user : users) {
            players.add(user.getLogin());
        }
        this.template.convertAndSend(String.format("/game/%s/play", gameId),
                gson.toJson(WebsocketEvent.builder().type(WebsocketEvent.EventType.PLAYERS).players(players).build()));
    }


}

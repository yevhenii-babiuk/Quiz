package com.qucat.quiz.controllers;

import com.google.gson.Gson;
import com.qucat.quiz.repositories.dto.quizplay.AnswerDto;
import com.qucat.quiz.repositories.dto.quizplay.Game;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.QuestionType;
import com.qucat.quiz.repositories.dto.quizplay.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONStringer;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayGameController {
    @Autowired
    private SimpMessagingTemplate template;

    private List<String> pl = new ArrayList<>();

    @MessageMapping("/{gameId}/play")
    public void onReceiveAnswer(@DestinationVariable String gameId, String message) {
        Gson g = new Gson();
        AnswerDto answerDto = g.fromJson(message, AnswerDto.class);
        System.out.println(answerDto);
    }

    @MessageMapping("/{gameId}/start")
    public void onReceiveMessage(@DestinationVariable String gameId, String message) {
        System.out.println(message);
        try {
            Thread.sleep(1000);
            sendQuestion(gameId, Question.builder().id(1).content("content1").type(QuestionType.TRUE_FALSE).score(20).build());
            Thread.sleep(18000);
            sendResults(gameId);
            Thread.sleep(5000);
            sendQuestion(gameId, Question.builder().id(2).content("content2").type(QuestionType.TRUE_FALSE).score(20).build());
            Thread.sleep(18000);
            sendResults(gameId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("api/v1/game")
    public int addGame(@RequestBody Game game) {
        //service.createGame()
        System.out.println(game);
        return 0;
    }

    @GetMapping("api/v1/game/{gameId}")
    public Game getJoinedUsers(@PathVariable String gameId) {
        return Game.builder().gameId(gameId).build();
    }

    @PostMapping("api/v1/game/{gameId}/joinedUser")
    public UserDto addJoinedUser(@PathVariable String gameId, @RequestBody int userId) {
        //service.createGame()
        System.out.println("JOINED USER game id= " + gameId);
        System.out.println("user id= " + userId);
        if (userId != 0) pl.add("authorisedUser" + userId);
        else pl.add("unauthorisedUser" + userId);
        sendPlayers(gameId, pl);

        if (userId != 0) return UserDto.builder().id(userId).login("authorisedUser" + userId).build();
        return UserDto.builder().id(userId).login("unauthorisedUser" + userId).build();
    }

    @GetMapping("api/v1/game/{gameId}/joinedUser")
    public List<String> getJoinedUsers(@PathVariable int gameId) {
        //service.createGame()
        System.out.println("game id= " + gameId);
        return pl;
    }

    private void sendPlayers(String gameId, List<String> players) {
        JSONStringer stringer = new JSONStringer();
        try {
            stringer.array();
            for (String p : players) {
                stringer.value(p);
            }
            stringer.endArray();
            this.template.convertAndSend(String.format("/game/%s/players", gameId), stringer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendQuestion(String gameId, Question question) {
        System.out.println("send question");
        Gson g = new Gson();
        this.template.convertAndSend(String.format("/game/%s/play/question", gameId), g.toJson(question));
    }

    private void sendResults(String gameId) {
        System.out.println("send results");
        Gson g = new Gson();
        this.template.convertAndSend(String.format("/game/%s/play/results", gameId), "results");
    }
}

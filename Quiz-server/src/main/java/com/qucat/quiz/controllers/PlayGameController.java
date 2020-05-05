package com.qucat.quiz.controllers;

import com.google.gson.Gson;
import com.qucat.quiz.repositories.dto.AnswerDto;
import com.qucat.quiz.repositories.dto.GameDto;
import com.qucat.quiz.repositories.dto.UserDto;
import com.qucat.quiz.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayGameController {

    @Autowired
    private GameService gameService;

    @MessageMapping("/{gameId}/play")
    public void onReceiveAnswer(@DestinationVariable String gameId, String message) {
        Gson g = new Gson();
        AnswerDto answerDto = g.fromJson(message, AnswerDto.class);
        System.out.println(answerDto);
        gameService.setAnswer(answerDto);
    }

    @MessageMapping("/{gameId}/start")
    public void onReceiveMessage(@DestinationVariable String gameId, String message) {
        System.out.println(message);
        gameService.startGame(gameId);
    }

    @PostMapping("api/v1/game")
    public String addGame(@RequestBody GameDto game) {
        Gson gson = new Gson();
        return gson.toJson(gameService.createRoom(game));
    }

    @GetMapping("api/v1/game/{gameId}")
    public GameDto getGameById(@PathVariable String gameId) {
        return gameService.getGameById(gameId);
    }

    @PostMapping("api/v1/game/{gameId}/joinedUser")
    public UserDto addJoinedUser(@PathVariable String gameId, @RequestBody int userId) {
        System.out.println("JOINED USER game id= " + gameId);
        System.out.println("user id= " + userId);
        return gameService.connectUser(gameId, userId);
    }

    @GetMapping("api/v1/game/{gameId}/joinedUser")
    public List<String> getJoinedUsers(@PathVariable int gameId) {
        return null;
    }

}

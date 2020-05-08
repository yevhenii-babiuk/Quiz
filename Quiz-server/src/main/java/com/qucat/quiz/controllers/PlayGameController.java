package com.qucat.quiz.controllers;

import com.google.gson.Gson;
import com.qucat.quiz.repositories.dto.AnswerDto;
import com.qucat.quiz.repositories.dto.GameDto;
import com.qucat.quiz.repositories.dto.UserDto;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.services.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PlayGameController {

    @Autowired
    private GameService gameService;

    @MessageMapping("/{gameId}/play")
    public void onReceiveAnswer(@DestinationVariable String gameId, String message) {
        Gson g = new Gson();
        AnswerDto answerDto = g.fromJson(message, AnswerDto.class);
        gameService.setAnswer(answerDto);
    }

    @MessageMapping("/{gameId}/start")
    public void onReceiveMessage(@DestinationVariable String gameId, String message) {
        gameService.startGame(gameId);
    }

    @PostMapping("api/v1/game")
    public String addGame(@RequestBody GameDto game) {
        log.info("get by api/v1/game" + game.toString());
        Gson gson = new Gson();
        return gson.toJson(gameService.createRoom(game));
    }

    @GetMapping("api/v1/game/{gameId}")
    public GameDto getGameById(@PathVariable String gameId) {
        return gameService.getGameById(gameId);
    }

    @GetMapping("api/v1/game/{gameId}/user/{userId}")
    public Question getGameById(@PathVariable String gameId, @PathVariable String userId) {
        return gameService.getCurrentQuestion(gameId, Integer.parseInt(userId));
    }


    @PostMapping("api/v1/game/{gameId}/joinedUser")
    public UserDto addJoinedUser(@PathVariable String gameId, @RequestBody int userId) {
        log.info("get by api/v1/game/" + gameId + "/joinedUser " + userId);
        return gameService.connectUser(gameId, userId);
    }

    @GetMapping("api/v1/game/{gameId}/joinedUser")
    public List<String> getJoinedUsers(@PathVariable int gameId) {
        return null;
    }

}

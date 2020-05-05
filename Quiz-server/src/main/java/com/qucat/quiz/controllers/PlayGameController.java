package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Game;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.services.PlayGameService;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONStringer;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class PlayGameController {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private PlayGameService playGameService;

    @MessageMapping("/{gameId}/play")
    public void onReceiveMessage(@DestinationVariable String gameId, String message) {
        System.out.println(message);
        //playGameService.sendQ(gameId);
       /* String destination = "/game/%s/user/%s";
        destination = String.format(destination, gameId, "72");
        System.out.println(destination);
        JSONStringer stringer1 = new JSONStringer();
        try {
            stringer1.object();
            stringer1.key("command").value("question");
            stringer1.key("question").value(Question.builder().content("content").score(20).build());
            stringer1.endObject();
            this.template.convertAndSend(destination, stringer1.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

      /*  JSONStringer stringer2 = new JSONStringer();
        try {
            stringer2.object();
            stringer2.key("command").value("answer");
            stringer2.key("answer").value(QuestionOption.builder().content("content").id(5).build());
            stringer2.endObject();
            this.template.convertAndSend("/game/6", stringer2.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        // this.template.convertAndSend("/game/6", Question.builder().content("another").score(10).build());

    }

    @MessageMapping("/game/{gameId}/connect")
    public void onConnect(@DestinationVariable String gameId, String id) {
        System.out.println("CONNECT game id= " + gameId);
        System.out.println("user id= " + id);
    }

    @PostMapping("/game")
    public int addGame(@RequestBody Game game) {
        //service.createGame()
        System.out.println(game);
        return 0;
    }

    @PostMapping("/game/{gameId}/joinedUser")
    public User addJoinedUser(@PathVariable int gameId, @RequestBody int userId) {
        //service.createGame()
        System.out.println("JOINED USER game id= " + gameId);
        System.out.println("user id= " + userId);
        sendPlayers(gameId, List.of("user1", "user2", "user3", "user4", "authorisedUser" + userId));

        if (userId != 0) return User.builder().userId(userId).login("authorisedUser" + userId).build();
        return User.builder().userId(userId).login("unauthorisedUser" + userId).build();
    }

    @GetMapping("/game/{gameId}/joinedUser")
    public List<String> getJoinedUsers(@PathVariable int gameId) {
        //service.createGame()
        System.out.println("game id= " + gameId);
        return List.of("user1", "user2", "user3", "user4");
    }

    private void sendPlayers(int gameId, List<String> players) {
        System.out.println("SEND PLAYERS");
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
}

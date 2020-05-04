package com.qucat.quiz.controllers;

import com.qucat.quiz.services.PlayGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PlayGameController {


    @Autowired
    private PlayGameService playGameService;

    @MessageMapping("/{gameId}/play")
    public void onReceiveMessage(@DestinationVariable String gameId, String message) {
        System.out.println(message);
        playGameService.sendQ(gameId);
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

    @MessageMapping("/{gameId}/connect")
    public void onConnect(@DestinationVariable String gameId, String id) {
        System.out.println("game id= " + gameId);
        System.out.println("user id= " + id);
    }
}

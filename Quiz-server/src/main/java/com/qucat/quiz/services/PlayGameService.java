package com.qucat.quiz.services;

import com.qucat.quiz.repositories.entities.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONStringer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlayGameService {
    @Autowired
    private SimpMessagingTemplate template;


    public void sendQ(String gameId) {
        JSONStringer stringer1 = new JSONStringer();
        try {
            stringer1.object();
            stringer1.key("command").value("question");
            stringer1.key("question").value(Question.builder().content(gameId).score(20).build());
            stringer1.endObject();
            this.template.convertAndSend(String.format("/game/%s/user/72", gameId), stringer1.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

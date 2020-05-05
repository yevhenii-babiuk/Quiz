package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dto.quizplay.UserDto;
import com.qucat.quiz.repositories.entities.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONStringer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WebSocketSenderService {

    @Autowired
    private SimpMessagingTemplate template;

    private String createJson(String key, String value){
        JSONStringer stringer = new JSONStringer();
        try {
            stringer.object();
            stringer.key("command").value(key);
            stringer.key(key).value(value);
            stringer.endObject();
        } catch (JSONException e) {
            log.error("cant create JSON", e);
        }
        return stringer.toString();
    }

    public void sendUsers(List<UserDto> users, String gameId){
        JSONStringer stringer = new JSONStringer();
        try {
            stringer.array();
            for (UserDto p : users) {
                stringer.value(p);
            }
            stringer.endArray();
            this.template.convertAndSend(String.format("/game/%s/players", gameId), stringer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendQuestion(Question question, String gameId){
        this.template.convertAndSend("/game/"+gameId,
                createJson("question", question.toString()));

    }





}

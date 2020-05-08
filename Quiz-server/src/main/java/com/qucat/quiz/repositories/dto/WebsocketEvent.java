package com.qucat.quiz.repositories.dto;

import com.qucat.quiz.repositories.entities.Question;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WebsocketEvent {
    public enum EventType{
        RESULTS,
        QUESTION,
        PLAYERS
    }
    private EventType type;
    private List<String> players;
    private Question question;
    private Users results;

}

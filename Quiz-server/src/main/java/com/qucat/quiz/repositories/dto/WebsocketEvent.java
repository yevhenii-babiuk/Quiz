package com.qucat.quiz.repositories.dto;

import com.qucat.quiz.repositories.dto.game.Users;
import com.qucat.quiz.repositories.entities.Question;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WebsocketEvent {
    private EventType type;
    private List<String> players;
    private Question question;
    private Users gameResults;
    private int currQuestion;

    public enum EventType {
        RESULTS,
        QUESTION,
        PLAYERS
    }

}

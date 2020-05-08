package com.qucat.quiz.repositories.dto;

import com.qucat.quiz.repositories.entities.Question;

import java.util.List;

public class WebsocketEvent {
    public enum EventType{
        RESULTS,
        QUESTION,
        PLAYERS
    }
    private List<String> players;
    private Question question;
    private List<UserDto> results;
}
package com.qucat.quiz.repositories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private int id;
    private String gameId;
    private String login;
    private int registerId;
    private int score;
    private int comboAnswer;

}

package com.qucat.quiz.repositories.dto.quizplay;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private int id;
    private String gameId;
    private String login;
    private int registerId;
    private int score;

}

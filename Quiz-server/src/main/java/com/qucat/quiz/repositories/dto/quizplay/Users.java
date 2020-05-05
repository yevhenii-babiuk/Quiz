package com.qucat.quiz.repositories.dto.quizplay;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Users {
    private List<UserDto> users;
}

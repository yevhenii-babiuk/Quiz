package com.qucat.quiz.repositories.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Users {
    private List<UserDto> users;
}

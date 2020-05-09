package com.qucat.quiz.repositories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Users {
    private List<UserDto> users;
}

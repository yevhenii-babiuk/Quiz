package com.qucat.quiz.repositories.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Users {
    private List<UserDto> users;
    @JsonProperty("isFinal")
    private boolean isFinal;
}

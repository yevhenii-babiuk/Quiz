package com.qucat.quiz.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class Token {

    private String token;
    private TokenType tokenType;
    private Date expiredDate;
    private int userId;
}

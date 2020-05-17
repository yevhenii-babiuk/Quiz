package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Token;

public interface TokenDao extends GenericDao<Token> {
    int getUserId(Token token);

    void deleteOldTokens();
}

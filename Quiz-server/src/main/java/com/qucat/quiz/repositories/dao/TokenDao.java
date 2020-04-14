package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Token;

public interface TokenDao extends Dao<Token>{
    int getUserId(Token token);

}

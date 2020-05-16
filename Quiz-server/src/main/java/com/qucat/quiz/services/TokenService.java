package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.TokenDao;
import com.qucat.quiz.repositories.entities.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenService {


    @Autowired
    private TokenDao tokenDao;


    public void deleteOldTokens() {
        tokenDao.deleteOldTokens();
    }

    public void saveToken(Token tokenForNewUser) {
        tokenDao.save(tokenForNewUser);
    }

    public int getUserId(Token token) {
        return tokenDao.getUserId(token);
    }

    public Token getTokenByUserId(int id) {
        return tokenDao.get(id);
    }

}

package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.TokenDao;
import com.qucat.quiz.repositories.entities.Token;
import com.qucat.quiz.repositories.entities.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class TokenDaoImpl implements TokenDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Token get(int id) {
        String selectQuery = "SELECT * FROM system_action_tokens WHERE user_id=?;";
        Token token;
        try {
            token = jdbcTemplate.queryForObject(selectQuery,
                    new Object[]{id},
                    (resultSet, rowNum) ->
                            new Token(resultSet.getString("token"),
                                    TokenType.valueOf(resultSet.getString("token_type").toUpperCase()),
                                    resultSet.getDate("expired_date"),
                                    resultSet.getInt("user_id")));

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return token;
    }

    @Override
    public int getUserId(Token token) {
        String userIdQuery = "SELECT user_id FROM system_action_tokens " +
                "WHERE token=? AND token_type=cast(? AS system_action_token_type)" +
                " AND expired_date > NOW();";
        int id;
        try {
            id = jdbcTemplate.queryForObject(userIdQuery,
                    new Object[]{token.getToken(), token.getTokenType().name().toLowerCase()},
                    Integer.class);
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return 0;
        }

        return id;
    }

    @Override
    public List<Token> getAll() {
        return null;
    }

    @Override
    public int save(Token token) {
        String saveQuery = "INSERT INTO system_action_tokens" +
                "(token, token_type, user_id, expired_date)" +
                "VALUES (?,  cast(? AS system_action_token_type), ?, NOW() + interval '1 day');";
        try {
            jdbcTemplate.update(saveQuery,
                    token.getToken(), token.getTokenType().name().toLowerCase(), token.getUserId());
        } catch (Exception e) {
            return -1;
        }
        return 0;

    }

    @Override
    public void update(Token token) {

    }

    @Override
    public void delete(Token token) {

    }
}

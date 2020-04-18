package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.TokenDao;
import com.qucat.quiz.repositories.entities.Token;
import com.qucat.quiz.repositories.entities.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class TokenDaoImpl implements TokenDao {

    @Value("#{${sql.tokens}}")
    private Map<String, String> tokensQueries;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Token get(int id) {
        String selectQuery = tokensQueries.get("getByUserId");
        Token token;
        try {
            token = jdbcTemplate.queryForObject(selectQuery,
                    new Object[]{id}, new TokenRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return token;
    }

    @Override
    public int getUserId(Token token) {
        String userIdQuery = tokensQueries.get("getUserId");
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
        String saveQuery = "INSERT INTO system_action_tokens (token, token_type, user_id, expired_date) "
                + "VALUES (?,  cast(? AS system_action_token_type), ?, NOW() + interval '1 day');";

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

    @Override
    public void deleteById(int id) {

    }

    private class TokenRowMapper implements RowMapper<Token> {

        @Override
        public Token mapRow(ResultSet resultSet, int i) throws SQLException {
            return Token.builder()
                    .token(resultSet.getString("token"))
                    .tokenType(TokenType.valueOf(resultSet.getString("token_type").toUpperCase()))
                    .expiredDate(resultSet.getDate("expired_date"))
                    .userId(resultSet.getInt("user_id"))
                    .build();
        }
    }
}

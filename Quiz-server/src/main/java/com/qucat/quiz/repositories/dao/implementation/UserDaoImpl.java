package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.UserDao;
import com.qucat.quiz.repositories.entities.Role;
import com.qucat.quiz.repositories.entities.TokenType;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.UserAccountStatus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String INTERVAL = "1 day";

    @Override
    public Optional<User> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users;", (resultSet, rowNum) ->
                new User(resultSet.getInt("user_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("second_name"),
                        resultSet.getString("login"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("profile"),
                        resultSet.getDate("registered_date"),
                        resultSet.getInt("total_score"),
                        UserAccountStatus.valueOf(resultSet.getString("status").toUpperCase()),
                        Role.valueOf(resultSet.getString("role").toUpperCase())
                )
        );
    }

    @Override
    public Optional<Integer> save(User user) {
        System.out.println("Hello");
        try {
            jdbcTemplate.update(
                    "INSERT INTO users " +
                            "(login, password, email, status, role, first_name, second_name, registered_date, profile, total_score) " +
                            "VALUES (?, ?, ?, cast(? AS profile_status), cast(? AS user_role), ?, ?, ?, ?, ?);",
                    user.getLogin(), user.getPassword(), user.getMail(), user.getStatus().name().toLowerCase(), user.getRole().name().toLowerCase(), user.getFirstName(), user.getSecondName(),
                    user.getRegistrationDate(), user.getProfile(), user.getScore());

            return Optional.of(jdbcTemplate.query("SELECT user_id FROM users WHERE login=?;",
                    new Object[]{user.getLogin()},
                    (resultSet, rowNum) -> resultSet.getInt("user_id")).get(0));
        }
        catch (RuntimeException e){
            return Optional.empty();
        }
    }

    public void setStatus(int id, UserAccountStatus status) {
        jdbcTemplate.update("UPDATE users " +
                        "SET status=cast(? AS profile_status) " +
                        "WHERE user_id=?",
                status.name().toLowerCase(), id);
    }

    public void setPassword(int id, String password) {
        jdbcTemplate.update("UPDATE users " +
                        "SET password=? " +
                        "WHERE user_id=?",
                password, id);
    }

    public void saveToken(String token, TokenType tokenType, int userID) {
        jdbcTemplate.update(
                "INSERT INTO system_action_tokens" +
                        "(token, token_type, user_id, expired_date)" +
                        "VALUES (?,  cast(? AS system_action_token_type), ?, NOW() + interval ?);",
                token, tokenType.name().toLowerCase(), userID, INTERVAL);
    }

    public Optional<Integer> getId(String token, TokenType tokenType) {
        List<Integer> id=jdbcTemplate.query("SELECT user_id FROM system_action_tokens " +
                        "WHERE token=? AND token_type=cast(? AS system_action_token_type)" +
                        " AND expired_date > NOW();",
                new Object[]{token, tokenType.name().toLowerCase()},
                (resultSet, rowNum) -> resultSet.getInt("user_id"));
        if(id.isEmpty())
            return Optional.empty();
        return Optional.of(id.get(0));
    }

    public Optional<User> getUser(String login, String password) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users " +
                        "WHERE login=? AND password=?;",
                new Object[]{login, password},
                (resultSet, rowNum) ->
                        new User(resultSet.getInt("user_id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("second_name"),
                                resultSet.getString("login"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("profile"),
                                resultSet.getDate("registered_date"),
                                resultSet.getInt("total_score"),
                                UserAccountStatus.valueOf(resultSet.getString("status").toUpperCase()),
                                Role.valueOf(resultSet.getString("role").toUpperCase())
                        )
        );
        if (users.isEmpty()) return Optional.empty();
        return Optional.of(users.get(0));
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }
}

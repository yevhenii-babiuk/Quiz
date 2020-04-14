package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.UserDao;
import com.qucat.quiz.repositories.entities.Role;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.UserAccountStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public User get(int id) {
        User user;
        try {
            user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id=?;",
                    new Object[]{id},
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
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
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
    public int save(User user) {
        String insertQuery = "INSERT INTO users " +
                "(login, password, email, status, role, first_name, second_name, registered_date, profile, total_score) " +
                "VALUES (?, ?, ?, cast(? AS profile_status), cast(? AS user_role), ?, ?, NOW(), ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection
                        .prepareStatement(insertQuery);
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getMail());
                preparedStatement.setString(4, user.getStatus().name().toLowerCase());
                preparedStatement.setString(5, user.getRole().name().toLowerCase());
                preparedStatement.setString(6, user.getFirstName());
                preparedStatement.setString(7, user.getSecondName());
                preparedStatement.setString(8, user.getProfile());
                preparedStatement.setInt(9, user.getScore());
                return preparedStatement;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            return -1;
        }
        return (int) keyHolder.getKey();
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        User user;
        try {
            user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE login=? AND password=?;",
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
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }

    @Override
    public User getUserByMail(String mail) {
        User user;
        try {
            user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?;",
                    new Object[]{mail},
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
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }

    @Override
    public void update(User user) {
        String updateQuery = "UPDATE users SET " +
                "login = ?, password = ?, email = ?, status = cast(? AS profile_status), " +
                "role = cast(? AS user_role), first_name = ?, " +
                "second_name = ?, registered_date = ?, profile = ?, total_score =? " +
                "WHERE user_id = ?;";
        jdbcTemplate.update(updateQuery, user.getLogin(), user.getPassword(), user.getMail(),
                user.getStatus().name().toLowerCase(), user.getRole().name().toLowerCase(),
                user.getFirstName(), user.getSecondName(), user.getRegistrationDate(),
                user.getProfile(), user.getScore(), user.getUserId());
    }

    @Override
    public void delete(User user) {

    }
}

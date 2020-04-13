package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.UserDao;
import com.qucat.quiz.repositories.entities.Role;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.UserAccountStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
                        UserAccountStatus.valueOf(resultSet.getString("status")),
                        Role.valueOf(resultSet.getString("role"))
                )
        );
    }

    @Override
    public long save(User user) {
        System.out.println("Hello");
       return jdbcTemplate.update(
                "INSERT INTO users " +
                        "(login, password, email, status, role, first_name, second_name, registered_date, profile, total_score) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                user.getLogin(), user.getPassword(), user.getMail(), user.getStatus().name(), user.getRole().name(), user.getFirstName(), user.getSecondName(),
                user.getRegistrationDate(), user.getProfile(), user.getScore());
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }
}

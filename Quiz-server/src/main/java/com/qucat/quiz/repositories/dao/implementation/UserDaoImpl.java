package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.UserDao;
import com.qucat.quiz.repositories.dao.mappers.UserMapper;
import com.qucat.quiz.repositories.entities.Role;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.UserAccountStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    @Value("#{${sql.users}}")
    private Map<String, String> usersQueries;

    protected UserDaoImpl() {
        super(new UserMapper(), TABLE_NAME);
    }


    @Override
    protected String getInsertQuery() {
        return usersQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getMail());
        preparedStatement.setString(4,
                user.getStatus() != null
                        ? user.getStatus().name().toLowerCase()
                        : UserAccountStatus.UNACTIVATED.name().toLowerCase());
        preparedStatement.setString(5,
                user.getRole() != null
                        ? user.getRole().name().toLowerCase()
                        : Role.USER.name().toLowerCase());
        preparedStatement.setString(6, user.getFirstName());
        preparedStatement.setString(7, user.getSecondName());
        if (user.getProfile() != null) {
            preparedStatement.setString(8, user.getProfile());
        } else {
            preparedStatement.setNull(8, Types.VARCHAR);
        }
        if (user.getScore() != 0) {
            preparedStatement.setInt(9, user.getScore());
        } else {
            preparedStatement.setNull(9, Types.INTEGER);
        }
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return usersQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(User user) {
        return new Object[]{user.getLogin(), user.getPassword(), user.getMail(),
                user.getStatus().name().toLowerCase(), user.getRole().name().toLowerCase(),
                user.getFirstName(), user.getSecondName(), user.getRegistrationDate(),
                user.getProfile(), user.getScore(), user.getUserId()};
    }


    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(usersQueries.get("selectByLoginAndPassword"),
                    new Object[]{login, password}, new UserMapper()
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
            user = jdbcTemplate.queryForObject(usersQueries.get("selectByMail"),
                    new Object[]{mail}, new UserMapper());
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }

    @Override
    public Page<User> getUserByRole(Role role, Pageable pageable) {
        int rowTotal = jdbcTemplate.queryForObject(usersQueries.get("rowCount"),
                new Object[]{role.name().toLowerCase()},
                (resultSet, number) -> resultSet.getInt(1));
        List<User> users = jdbcTemplate.query(usersQueries.get("getPageByRole"),
                new UserMapper());
        return new PageImpl<>(users, pageable, rowTotal);
    }

}

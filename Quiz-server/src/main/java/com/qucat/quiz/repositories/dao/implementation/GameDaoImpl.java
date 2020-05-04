package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.GameDao;
import com.qucat.quiz.repositories.dao.mappers.AnswerExtractor;
import com.qucat.quiz.repositories.dao.mappers.QuestionMapper;
import com.qucat.quiz.repositories.dao.mappers.UserDtoMapper;
import com.qucat.quiz.repositories.dto.quizplay.*;
import com.qucat.quiz.repositories.entities.Image;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.QuestionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class GameDaoImpl implements GameDao {

    @Value("#{${sql.game}}")
    private Map<String, String> queries;

    @Autowired
    @Qualifier("h2JdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<UserDto> getUsersByGame(String id) {
        String selectQuery = queries.get("getUsersByGameId");
        return jdbcTemplate.query(selectQuery,
                new Object[]{id}, new UserDtoMapper());
    }

    public List<AnswerDto> getAnswersToCurrentQuestionByGameId(String id) {
        String selectQuery = queries.get("getAnswersToCurrentQuestionByGameId");
        return jdbcTemplate.query(selectQuery,
                new Object[]{id}, new AnswerExtractor());
    }

    public Question getCurrentQuestionByGameId(String id) {
        String selectQuery = queries.get("getCurrentQuestionByGameId");
        return jdbcTemplate.queryForObject(selectQuery,
                new Object[]{id}, new QuestionMapper());
    }

    public Question getQuestionById(int id) {
        String selectQuery = queries.get("getQuestionById");
        return jdbcTemplate.queryForObject(selectQuery,
                new Object[]{id}, new QuestionMapper());
    }

    public int getHostId(String gameId) {
        return jdbcTemplate.queryForObject(queries.get("getHostId"),
                new Object[]{gameId}, Integer.class);
    }

    public int saveUser(UserDto user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection
                        .prepareStatement(queries.get("saveUser"), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, user.getGameId());
                preparedStatement.setString(2, user.getLogin());
                preparedStatement.setInt(3, user.getRegisterId());
                return preparedStatement;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            return -1;
        }
        return (int) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    public int saveAnswer(AnswerDto answer) {
        return jdbcTemplate.update(
                queries.get("saveAnswer"), answer.getUser().getId(),
                answer.getAnswer(), answer.getQuestion().getId(),
                answer.isCorrect(), answer.getTime());
    }

    public int saveSettings(GameDto game) {
        return jdbcTemplate.update(
                queries.get("saveSettings"), game.getGameId(),
                game.getTime(), game.isQuestionAnswerSequence(),
                game.isCombo(), game.isIntermediateResult(),
                game.isQuickAnswerBonus());
    }

    public void saveQuiz(QuizDto quiz) {
        jdbcTemplate.update(
                queries.get("saveQuiz"), quiz.getId(),
                quiz.getName(), quiz.getQuestionNumber(),
                quiz.getImageId());
    }

    public void saveQuestion(Question question) {
        jdbcTemplate.update(queries.get("saveQuestion"),
                question.getId(),
                question.getQuizId(), question.getType().name().toLowerCase(),
                question.getContent(), question.getScore(),
                question.getImageId());
    }

    public void saveOption(QuestionOption option) {
        jdbcTemplate.update(queries.get("saveOption"),
                option.getId(),
                option.getQuestionId(), option.getContent(),
                option.isCorrect(), option.getSequenceOrder(),
                option.getImageId());
    }

    public void saveImage(Image image) {
        jdbcTemplate.update(queries.get("saveImage"),
                image.getId(), image.getSrc());
    }

    public int saveGameQuestion(String gameId, int questionId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection
                        .prepareStatement(queries.get("saveGameQuestion"), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, gameId);
                preparedStatement.setInt(2, questionId);
                return preparedStatement;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            return -1;
        }
        return (int) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    public void updateGameQuestion(GameQuestionDto gameQuestion) {
        jdbcTemplate.update(queries.get("updateGameQuestion"),
                gameQuestion.getGameId(), gameQuestion.getQuestionId(),
                gameQuestion.isCurrent(), gameQuestion.getId());
    }

    public void deleteGame(String id){
        jdbcTemplate.update(queries.get("deleteGame"), id);
    }

    public void saveGame(int quizId, String gameId) {
        jdbcTemplate.update(queries.get("saveGame"),
                gameId, quizId);
    }
}

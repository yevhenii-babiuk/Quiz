package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.*;
import com.qucat.quiz.repositories.dao.mappers.QuizMapper;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.QuizStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class QuizDaoImpl extends GenericDaoImpl<Quiz> implements QuizDao {
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private ImageDao imageDao;

    @Value("#{${sql.quiz}}")
    private Map<String, String> quizQueries;

    protected QuizDaoImpl() {
        super(new QuizMapper(), TABLE_NAME);
    }

    @Override
    protected String getInsertQuery() {
        return quizQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, Quiz quiz) throws SQLException {
        preparedStatement.setString(1, quiz.getName());
        preparedStatement.setInt(2, quiz.getAuthorId());
        preparedStatement.setInt(3, quiz.getCategoryId());
        preparedStatement.setString(4,
                quiz.getStatus() != null
                        ? quiz.getStatus().name().toLowerCase()
                        : QuizStatus.UNSAVED.name().toLowerCase());
        if (quiz.getPublishedDate() != null) {
            preparedStatement.setTimestamp(5, new Timestamp(quiz.getPublishedDate().getTime()));
        } else {
            preparedStatement.setNull(5, Types.TIMESTAMP);
        }
        if (quiz.getUpdatedDate() != null) {
            preparedStatement.setTimestamp(6, new Timestamp(quiz.getUpdatedDate().getTime()));
        } else {
            preparedStatement.setNull(6, Types.TIMESTAMP);
        }
        preparedStatement.setInt(7, quiz.getQuestionNumber());
        preparedStatement.setInt(8, quiz.getMaxScore());
        preparedStatement.setInt(9, quiz.getImageId());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return quizQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(Quiz quiz) {
        return new Object[]{quiz.getName(), quiz.getAuthorId(), quiz.getCategoryId(), quiz.getStatus().name().toLowerCase(),
                quiz.getPublishedDate(), quiz.getUpdatedDate(),
                quiz.getCreatedDate(), quiz.getQuestionNumber(), quiz.getMaxScore(), quiz.getImageId(), quiz.getId()};
    }

    @Override
    public Quiz getFullInformation(int id) {
        Quiz quiz = get(id);
        if (quiz != null) {
            quiz.setCategory(categoryDao.get(quiz.getCategoryId()));
            quiz.setTags(tagDao.getByQuizId(id));
            quiz.setQuestions(questionDao.getByQuizId(id));
            for (Question q : quiz.getQuestions()) {
                questionDao.getFullInformation(q);
            }
        }
        return quiz;
    }

    @Override
    public Quiz getFullInformation(Quiz quiz) {
        if (quiz != null) {
            quiz.setCategory(categoryDao.get(quiz.getCategoryId()));
            quiz.setTags(tagDao.getByQuizId(quiz.getId()));
            quiz.setQuestions(questionDao.getByQuizId(quiz.getId()));
            quiz.setImage(imageDao.get(quiz.getImageId()));
            for (Question q : quiz.getQuestions()) {
                questionDao.getFullInformation(q);

            }
        }
        return quiz;
    }

    @Override
    public boolean addTag(int quizId, int tagId) {
        try {
            jdbcTemplate.update(
                    quizQueries.get("addTag"),
                    quizId, tagId
            );
        } catch (DuplicateKeyException e) {
            return false;
        }
        return true;
    }

    @Override
    public Page<Quiz> getQuizByStatus(QuizStatus status, Pageable pageable) {
        int rowTotal = jdbcTemplate.queryForObject(quizQueries.get("rowCountByStatus"),
                new Object[]{status.name().toLowerCase()}, Integer.class);
        List<Quiz> quizzes = jdbcTemplate.query(quizQueries.get("getPageByStatus"),
                new QuizMapper());
        return new PageImpl<>(quizzes, pageable, rowTotal);
    }

    @Override
    public Page<Quiz> findAll(Pageable pageable) {
        int total = jdbcTemplate.queryForObject(quizQueries.get("rowCount"),
                new Object[]{},
                (resultSet, number) -> resultSet.getInt(1));
        System.out.println(total);
        List<Quiz> quizzes = jdbcTemplate.query(quizQueries.get("getPageAllQuizzes"),
                new Object[]{ pageable.getPageSize(), pageable.getOffset()},
                new QuizMapper());
        return new PageImpl<>(quizzes, pageable, total);
    }

    @Override
    public Page<Quiz> findAllByName(String name, Pageable pageable) {
        int total = jdbcTemplate.queryForObject(quizQueries.get("nameRowCount"),
                new Object[]{},
                (resultSet, number) -> resultSet.getInt(1));

        List<Quiz> quizzes = jdbcTemplate.query(quizQueries.get("getPageByName"),
                new Object[]{ pageable.getPageSize(), pageable.getOffset()},
                new QuizMapper());
        return new PageImpl<>(quizzes, pageable, total);
    }
}

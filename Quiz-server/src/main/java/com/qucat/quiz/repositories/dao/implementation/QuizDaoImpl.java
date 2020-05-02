package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.QuizDao;
import com.qucat.quiz.repositories.dao.mappers.QuizExtractor;
import com.qucat.quiz.repositories.dao.mappers.QuizMapper;
import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.QuizStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@PropertySource("classpath:quiz.properties")
public class QuizDaoImpl extends GenericDaoImpl<Quiz> implements QuizDao {

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
    public List<Quiz> getAllFullInfo() {
        return jdbcTemplate.query(quizQueries.get("getFullInfo"), new QuizExtractor());
    }

    @Override
    public Quiz getFullInfo(int id) {
        String getQuery = quizQueries.get("getFullInfo").replace(";", " WHERE quiz.id = ?;");
        List<Quiz> result = jdbcTemplate.query(getQuery, new Object[]{id}, new QuizExtractor());
        return result.size() == 0 ? null : result.get(0);
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

    private PreparedStatement getPagePreparedStatement(String query, List<Integer> id) {
        StringBuilder pageQuery = new StringBuilder(quizQueries.get("getFullInfo").replace(";", ""));

        if (id != null) {
            pageQuery.append(quizQueries.get("caseId"));

            String insertion = makeInsertion(id.size());
            pageQuery.replace(query.indexOf("(") + 1, query.indexOf(")") - 1, insertion);
        }

        return getParamForPreparedStatement(query.toString(), id, null, null, null, null,
                null, null, null, null);
    }

    private String makeInsertion(int size) {
        List<String> mark = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            mark.add("?");
        }
        return String.join(",", mark);
    }

    private PreparedStatement getParamForPreparedStatement(String query, List<Integer> id, Pageable pageable,
                                                           String name, String author, List<String> category,
                                                           Timestamp minDate, Timestamp maxDate, List<String> tags,
                                                           QuizStatus status) {

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(query);
            int paramIndex = 1;
            if (author != null) {
                preparedStatement.setString(paramIndex++, author);
            }
            if (name != null) {
                preparedStatement.setString(paramIndex++, name);
            }

            if (category != null) {
                for (String categoryItem : category) {
                    preparedStatement.setString(paramIndex++, categoryItem);
                }
            }

            if (minDate != null) {
                preparedStatement.setTimestamp(paramIndex++, minDate);
            }

            if (minDate != null) {
                preparedStatement.setTimestamp(paramIndex++, maxDate);
            }

            if (tags != null) {
                for (String tagItem : tags) {
                    preparedStatement.setString(paramIndex++, tagItem);
                }
            }
            if (pageable != null) {
                preparedStatement.setInt(paramIndex++, pageable.getPageSize());
                preparedStatement.setLong(paramIndex++, pageable.getOffset());
            }

            if (status != null) {
                preparedStatement.setString(paramIndex++, status.toString());
            }

            if (id != null) {
                for (Integer item : id) {
                    preparedStatement.setInt(paramIndex++, item);
                }
            }
        } catch (SQLException e) {
            log.error(" " + e.getMessage());
            e.printStackTrace();
        }
        return preparedStatement;
    }

    private PreparedStatement getQuizIdQuery(Pageable pageable, String name, String author, List<String> category,
                                             Timestamp minDate, Timestamp maxDate, List<String> tags, QuizStatus status) {

        StringBuilder query = new StringBuilder(quizQueries.get("getQuizId"));
        boolean anotherParameter = false;

        if (author != null | name != null | category != null | minDate != null | tags != null) {
            query.append(" WHERE");
        } else {
            query.append(quizQueries.get("caseAll"));

            return getParamForPreparedStatement(query.toString(), null, pageable, name, author, category,
                    minDate, maxDate, tags, status);
        }
        if (author != null) {
            query.append(quizQueries.get("caseAuthor"));
            anotherParameter = true;
        }

        if (name != null) {
            if (anotherParameter) {
                query.append(" AND ");
            }
            anotherParameter = true;
            query.append(quizQueries.get("caseName"));
        }

        if (category != null) {
            if (anotherParameter) {
                query.append(" AND ");
            }
            anotherParameter = true;

            query.append(quizQueries.get("caseCategory"));

            String insertion = makeInsertion(category.size());

            query.replace(query.indexOf("(") + 1, query.indexOf(")") - 1, insertion);
        }

        if (minDate != null & maxDate != null) {
            if (anotherParameter) {
                query.append(" AND ");
            }
            anotherParameter = true;
            query.append(quizQueries.get("caseDate"));
        }

        if (tags != null) {
            if (anotherParameter) {
                query.append(" AND ");
            }
            anotherParameter = true;
            query.append(quizQueries.get("caseTag"));

            String insertion = makeInsertion(tags.size());

            query.replace(query.lastIndexOf("(") + 1, query.lastIndexOf(")") - 1, insertion);
        }

        if (status != null) {
            if (anotherParameter) {
                query.append(" AND ");
            }
            anotherParameter = true;
            query.append(quizQueries.get("caseStatus"));
        }
        query.append(quizQueries.get("caseAll"));
        return getParamForPreparedStatement(query.toString(), null, pageable, name, author, category,
                minDate, maxDate, tags, status);
    }

    private PreparedStatement getQuizCount(String query, String name, String author, List<String> category,
                                           Timestamp minDate, Timestamp maxDate, List<String> tags, QuizStatus status) {
        StringBuilder countQuery = new StringBuilder(quizQueries.get("quizCount"));

        if (tags != null | author != null | name != null | category != null | minDate != null) {
            countQuery.append(query.substring(query.indexOf("W"), query.lastIndexOf("L")));
        }
        countQuery.append(";");

        return getParamForPreparedStatement(countQuery.toString(), null, null, name, author, category,
                minDate, maxDate, tags, status);

    }


    @Override
    public Page<Quiz> findAllForPage(Pageable pageable, String name, String author, List<String> category,
                                     Timestamp minDate, Timestamp maxDate, List<String> tags, QuizStatus status) {
        List<Integer> id = new ArrayList<>();
        List<Quiz> quizzes = new ArrayList<>();
        int rowsCount = 0;
        PreparedStatement psId = getQuizIdQuery(pageable, name, author, category,
                minDate, maxDate, tags, status);
        PreparedStatement psRows = getQuizCount(psId.toString(), name, author, category,
                minDate, maxDate, tags, status);

        try {
            ResultSet rs = psId.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt("quiz_id"));
            }
            PreparedStatement psForPage = getPagePreparedStatement(psId.toString(), id);
            QuizExtractor qe = new QuizExtractor();
            quizzes = qe.extractData(psForPage.executeQuery());

            ResultSet rsRowsNum = psRows.executeQuery();
            if (rsRowsNum != null) {
                rowsCount = rsRowsNum.getInt("quiz_id");
            }

        } catch (Exception e) {
            log.error("Error while read page of quiz from DB: " + e.getMessage());
        }
        return new PageImpl<>(quizzes, pageable, rowsCount);
    }
}

package com.qucat.quiz.repositories.dao.mappers;

import com.qucat.quiz.repositories.entities.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizPageMapper implements ResultSetExtractor<List<Quiz>> {
    @Override
    public List<Quiz> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Quiz> quizMap = new HashMap<>();
        Map<Integer, Tag> tagMap = new HashMap<>();

        while (rs.next()) {
            int quizId = rs.getInt("quiz_id");
            Quiz quiz = quizMap.get(quizId);
            if (quiz == null) {
                quiz = Quiz.builder()
                        .id(quizId)
                        .name(rs.getString("quiz_name"))
                        .authorId(rs.getInt("author_id"))
                        .categoryId(rs.getInt("category_id"))
                        .category(new Category(rs.getInt("category_id"), rs.getString("category_name")))
                        .status(QuizStatus.valueOf(rs.getString("status").toUpperCase()))
                        .publishedDate(rs.getTimestamp("published_date"))
                        .updatedDate(rs.getTimestamp("updated_date"))
                        .createdDate(rs.getTimestamp("created_date"))
                        .maxScore(rs.getInt("max_score"))
                        .imageId(rs.getInt("quiz_image_id"))
                        .image(new Image(rs.getInt("quiz_image_id"), rs.getString("quiz_image_src")))
                        .build();
                quizMap.put(quizId, quiz);
            }

            List<Tag> tags = quiz.getTags();
            if (tags == null) {
                tags = new ArrayList<>();
                quiz.setTags(tags);
            }
            int tagId = rs.getInt("tag_id");
            if (tagId != 0) {
                Tag tag = tagMap.get(tagId);
                if (tag == null) {
                    tag = new Tag(rs.getInt("tag_id"), rs.getString("tag_name"));
                    tags.add(tag);
                    tagMap.put(tagId, tag);
                }
            }
        }
        return new ArrayList<>(quizMap.values());
    }
}

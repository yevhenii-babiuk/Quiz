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

public class QuestionExtractor implements ResultSetExtractor<List<Question>> {
    @Override
    public List<Question> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Question> questionMap = new HashMap<>();
        Map<Integer, QuestionOption> optionMap = new HashMap<>();

        while (rs.next()) {

            int questionId = rs.getInt("question_id");
            Question question = questionMap.get(questionId);
            if (question == null) {
                question = Question.builder()
                        .id(rs.getInt("question_id"))
                        .quizId(rs.getInt("quiz_id"))
                        .type(QuestionType.valueOf(rs.getString("question_type").toUpperCase()))
                        .content(rs.getString("question_content"))
                        .score(rs.getInt("question_score"))
                        .imageId(rs.getInt("question_image_id"))
                        .image(new Image(rs.getInt("question_image_id"), rs.getString("question_image_src")))
                        .build();
                questionMap.put(questionId, question);
            }


            List<QuestionOption> options = question.getOptions();
            if (options == null) {
                options = new ArrayList<>();
                question.setOptions(options);
            }
            int optionId = rs.getInt("option_id");
            if (optionId != 0) {
                QuestionOption option = optionMap.get(optionId);
                if (option == null) {
                    option = QuestionOption.builder()
                            .id(rs.getInt("option_id"))
                            .questionId(questionId)
                            .content(rs.getString("option_content"))
                            .isCorrect(rs.getBoolean("option_is_correct"))
                            .sequenceOrder(rs.getInt("sequence_order"))
                            .imageId(rs.getInt("option_image_id"))
                            .image(new Image(rs.getInt("option_image_id"), rs.getString("option_image_src")))
                            .build();
                    options.add(option);
                    optionMap.put(optionId, option);
                }
            }


        }
        return new ArrayList<>(questionMap.values());
    }
}

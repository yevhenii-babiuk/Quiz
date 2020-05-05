package com.qucat.quiz.repositories.dao.mappers;

import com.qucat.quiz.repositories.dto.quizplay.AnswerDto;
import com.qucat.quiz.repositories.dto.quizplay.UserDto;
import com.qucat.quiz.repositories.entities.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerExtractor implements ResultSetExtractor<List<AnswerDto>> {
    @Override
    public List<AnswerDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, AnswerDto> answerMap = new HashMap<>();

        while (rs.next()) {
            int answerId = rs.getInt("answer_id");
            AnswerDto answer = answerMap.get(answerId);
            if (answer == null) {
                answer = AnswerDto.builder()
                        .id(answerId)
                        .isCorrect(rs.getBoolean("is_correct_answer"))
                        .questionId(rs.getInt("quiz_id"))
                        .time(rs.getInt("time"))
                        .answer(rs.getString("current_answer"))
                        .build();
                answerMap.put(answerId, answer);
            }

            UserDto user = answer.getUser();
            if (user == null) {
                user.setId(rs.getInt("user_id"));
                user.setGameId(rs.getString("game_id"));
                user.setRegisterId(rs.getInt("registered_id"));
                user.setLogin(rs.getString("login"));
                user.setScore(rs.getInt("user_score"));
                user.setComboAnswer(rs.getInt("combo_answer"));

            }

            int questionId = rs.getInt("question_id");
            Question question = answer.getQuestion();
            if (question == null) {
                question.setId(questionId);
                question.setQuizId(rs.getInt("quiz_id"));
                question.setType(QuestionType.valueOf(rs.getString("question_type").toUpperCase()));
                question.setContent(rs.getString("question_content"));
                question.setScore(rs.getInt("question_score"));
                question.setImageId(rs.getInt("question_image_id"));
                question.setImage(new Image(rs.getInt("question_image_id"), rs.getString("question_image_src")));
            }


/*            List<QuestionOption> options = question.getOptions();
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
                }
            }*/
        }
        return new ArrayList<>(answerMap.values());
    }
}

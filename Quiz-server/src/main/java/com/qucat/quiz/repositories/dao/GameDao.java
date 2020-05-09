package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.dto.AnswerDto;
import com.qucat.quiz.repositories.dto.GameDto;
import com.qucat.quiz.repositories.dto.GameQuestionDto;
import com.qucat.quiz.repositories.dto.QuizDto;
import com.qucat.quiz.repositories.dto.UserDto;
import com.qucat.quiz.repositories.entities.Image;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.QuestionOption;

import java.util.List;

public interface GameDao {

    List<UserDto> getUsersByGame(String id);

    List<AnswerDto> getAnswersToCurrentQuestionByGameId(String id);

    GameQuestionDto getCurrentQuestionByGameId(String id);

    Question getQuestionById(int id);

    int getHostId(String gameId);

    int saveUser(UserDto user);

    int saveAnswer(AnswerDto answer);

    int saveSettings(GameDto game);

    void saveQuiz(QuizDto quiz);

    void saveQuestion(Question question);

    void saveOption(QuestionOption option);

    void saveQuestions(List<Question> questions);

    void saveQuestionOptions(List<QuestionOption> questionOptions);

    void saveImage(Image image);

    int saveGameQuestion(String gameId, int questionId);

    void updateGameQuestion(GameQuestionDto gameQuestion);

    void deleteGame(String id);

    void saveGame(int quizId, String gameId, int hostId);

    GameDto getGame(String id);

    void updateUserDto(UserDto user);

    void updateGameQuestionToCurrent(int id);

    void deleteGameQuestion(int id);

    void updateUserToHost(int id);

    GameQuestionDto getGameQuestion(String gameId, int random);

    int getCountGameQuestion(String gameId);

    UserDto getUserById(int id);
}

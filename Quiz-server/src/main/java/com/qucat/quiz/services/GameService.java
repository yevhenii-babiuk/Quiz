package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.GameDaoImpl;
import com.qucat.quiz.repositories.dto.quizplay.*;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.QuestionOption;
import com.qucat.quiz.repositories.entities.Quiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class GameService {
    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @Autowired
    private GameDaoImpl gameDao;

    @Autowired
    private QuizService quizService;

    @Autowired
    private WebSocketSenderService socketSenderService;

    @Value("${url}")
    private String URL;

    public int connectUser(UserDto user) {
        int id = gameDao.saveUser(user);
        Users users = Users.builder().users(gameDao.getUsersByGame(user.getGameId())).build();
        socketSenderService.sendUsers(users, user.getGameId());
        return id;
    }

    public void startGame(String gameId) {
        GameProcess gameProcess = new GameProcess(gameId);
        gameProcess.run();
    }

    public void setAnswer(AnswerDto answer) {
        gameDao.saveAnswer(answer);
    }

    public String createRoom(GameDto game) {
        String gameId = String.format("%040d",
                new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)
        ).substring(0, 10);
        game.setGameId(gameId);
        gameDao.saveSettings(game);

        gameDao.saveGame(game.getQuizId(), game.getGameId());
        Quiz quiz = quizService.getQuizById(game.getQuizId());
        QuizDto quizDto = QuizDto.builder().id(quiz.getId()).image(quiz.getImage()).imageId(quiz.getImageId())
                .name(quiz.getName()).questionNumber(quiz.getQuestionNumber()).questions(quiz.getQuestions()).build();
        gameDao.saveQuiz(quizDto);
        gameDao.saveImage(quiz.getImage());
        for (Question question : quizDto.getQuestions()) {
            gameDao.saveQuestion(question);
            if (question.getImageId() != -1) {
                gameDao.saveImage(question.getImage());
            }
            gameDao.saveGameQuestion(game.getGameId(), question.getId());
            for (QuestionOption option : question.getOptions()) {
                gameDao.saveOption(option);
                if (option.getImageId() != -1) {
                    gameDao.saveImage(option.getImage());
                }
            }
        }

        return gameId;
    }

    public String getQRCode(int quizId, String accessCode) {
        return Base64.getEncoder().encodeToString(qrCodeGenerator.getQRCodeImage(
                URL + "quiz/" + quizId + "/game/" + accessCode,
                200, 200));
    }
/*
    public GameAccessor generateGameAccessCredentials(Quiz quiz) {
        String accessCode = String.format("%040d",
                new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)
        ).substring(0, 10);

        Image qrCode = new Image(
                Integer.parseInt(accessCode),
                Base64.getEncoder().encodeToString(qrCodeGenerator.getQRCodeImage(
                        URL + "quiz/" + quiz.getId() + "/game/" + accessCode,
                        200, 200))
        );

        return new GameAccessor(accessCode, qrCode);
    }*/
}

package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.GameDaoImpl;
import com.qucat.quiz.repositories.dto.AnswerDto;
import com.qucat.quiz.repositories.dto.GameDto;
import com.qucat.quiz.repositories.dto.QuizDto;
import com.qucat.quiz.repositories.dto.UserDto;
import com.qucat.quiz.repositories.dto.Users;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.QuestionOption;
import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.User;
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
    private UserService userService;

    @Autowired
    private GameProcess gameProcess;

    @Autowired
    private WebSocketSenderService socketSenderService;

    @Value("${url}")
    private String URL;

    public UserDto connectUser(String gameId, int userId) {
        UserDto user;
        if (userId != 0) {
            User registerUser = userService.getUserDataById(userId);
            user = UserDto.builder().login(registerUser.getLogin())
                    .registerId(registerUser.getUserId()).gameId(gameId).build();
        } else {
            user = UserDto.builder().gameId(gameId).login("player").registerId(userId).build();
        }
        user.setId(gameDao.saveUser(user));
        Users users = Users.builder().users(gameDao.getUsersByGame(user.getGameId())).build();
        socketSenderService.sendUsers(user.getGameId(),users);
        return user;
    }

    public void startGame(String gameId) {
        gameProcess.setGameId(gameId);
        gameProcess.run();
    }

    public void setAnswer(AnswerDto answer) {
        gameDao.saveAnswer(answer);
    }

    public GameDto getGameById(String gameID) {
        GameDto gameDto = gameDao.getGame(gameID);
        gameDto.setImage(getQRCode(gameDto.getQuizId(), gameID));
        return gameDto;
    }

    public String createRoom(GameDto game) {
        String gameId = String.format("%040d",
                new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)
        ).substring(0, 10);
        game.setGameId(gameId);

        Quiz quiz = quizService.getQuizById(game.getQuizId());
        QuizDto quizDto = QuizDto.builder().id(quiz.getId()).image(quiz.getImage()).imageId(quiz.getImageId())
                .name(quiz.getName()).questionNumber(quiz.getQuestionNumber()).questions(quiz.getQuestions()).build();
        gameDao.saveImage(quiz.getImage());
        gameDao.saveQuiz(quizDto);
        gameDao.saveGame(game.getQuizId(), game.getGameId(), game.getHostId());
        gameDao.saveSettings(game);
        gameDao.updateUserToHost(game.getHostId());
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

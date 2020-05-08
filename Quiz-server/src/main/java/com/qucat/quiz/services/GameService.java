package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.GameDaoImpl;
import com.qucat.quiz.repositories.dto.*;
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
import java.sql.Timestamp;
import java.util.*;

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
            user = UserDto.builder()
                    .login(registerUser.getLogin())
                    .registerId(registerUser.getUserId())
                    .gameId(gameId)
                    .build();
        } else {
            user = UserDto.builder()
                    .gameId(gameId)
                    .login("player")
                    .registerId(userId)
                    .build();
        }
        user.setId(gameDao.saveUser(user));
        socketSenderService.sendUsers(user.getGameId(), gameDao.getUsersByGame(user.getGameId()));
        return user;
    }

    public void startGame(String gameId) {
        gameProcess.setGameId(gameId);
        gameProcess.run();
    }


    private void calculateCorrectForAnswer(AnswerDto answer, Question question) {
        List<QuestionOption> options = question.getOptions();
        int correctAnswer = 0;
        switch (question.getType()) {
            case ENTER_ANSWER:
                if (options.get(0).getContent().trim()
                        .equalsIgnoreCase(answer.getFullAnswer().trim())) {
                    answer.setCorrect(true);
                    answer.setPercent(100);
                } else {
                    answer.setCorrect(false);
                    answer.setPercent(0);
                }
                break;
            case TRUE_FALSE:
                if (options.get(0).isCorrect() == answer.isTrueFalse()) {
                    answer.setCorrect(true);
                    answer.setPercent(100);
                } else {
                    answer.setCorrect(false);
                    answer.setPercent(0);
                }
                break;

            case SELECT_OPTION:
                List<Integer> chosenAnswers = answer.getOptions();
                for (QuestionOption option : question.getOptions()) {
                    if (option.isCorrect() == chosenAnswers.contains(option.getId())) {
                        correctAnswer++;
                    }
                }
                answer.setCorrect(correctAnswer == question.getOptions().size());
                answer.setPercent(100 * correctAnswer / question.getOptions().size());
                break;

            case SELECT_SEQUENCE:
                HashMap<Integer, Integer> sequence = answer.getSequence();
                for (QuestionOption option : question.getOptions()) {
                    if (option.getSequenceOrder() == sequence.get(option.getId())) {
                        correctAnswer++;
                    }
                }
                answer.setCorrect(correctAnswer == question.getOptions().size());
                answer.setPercent(100 * correctAnswer / question.getOptions().size());
                break;

            default:
                break;
        }
    }

    public void setAnswer(AnswerDto answer) {
        String gameID = answer.getGameId();
        answer.setTime(new Timestamp(new Date().getTime()));
        GameQuestionDto gameQuestionDto = gameDao.getCurrentQuestionByGameId(gameID);
        Question question = gameDao.getQuestionById(gameQuestionDto.getQuestionId());
        calculateCorrectForAnswer(answer, question);//todo smth with time
        gameDao.saveAnswer(answer);
    }

    public GameDto getGameById(String gameID) {
        GameDto gameDto = gameDao.getGame(gameID);
        gameDto.setImage(getQRCode(gameDto.getQuizId(), gameID));
        return gameDto;
    }


    public Question getCurrentQuestion(String gameId, int userId) {
        GameQuestionDto gameQuestionDto = gameDao.getCurrentQuestionByGameId(gameId);
        if (gameQuestionDto == null || gameQuestionDto.getQuestionId() == 0) return null;
        return gameDao.getQuestionById(gameQuestionDto.getQuestionId());//todo check timer. check answer
    }

    private String generateAccessCode() {
        return String.format("%040d",
                new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)
        ).substring(0, 10);
    }

    private void saveQuiz(GameDto game, QuizDto quizDto) {
        gameDao.saveImage(quizDto.getImage());
        gameDao.saveQuiz(quizDto);
        gameDao.saveGame(game.getQuizId(), game.getGameId(), game.getHostId());
        gameDao.saveSettings(game);
        gameDao.updateUserToHost(game.getHostId());
        gameDao.saveQuestions(quizDto.getQuestions());

        List<QuestionOption> questionOptions = new ArrayList<>();
        for (Question question : quizDto.getQuestions()) {
            questionOptions.addAll(question.getOptions());
        }
        gameDao.saveQuestionOptions(questionOptions);

        for (Question question : quizDto.getQuestions()) {//todo save gameQuestions
            gameDao.saveGameQuestion(game.getGameId(), question.getId());
        }


        /*for (Question question : quizDto.getQuestions()) {
            gameDao.saveQuestion(question);
            if (question.getImageId() != -1) {
                gameDao.saveImage(question.getImage());
            }

            for (QuestionOption option : question.getOptions()) {
                gameDao.saveOption(option);
                if (option.getImageId() != -1) {
                    gameDao.saveImage(option.getImage());
                }
            }
        }*/

    }

    public String createRoom(GameDto game) {
        String gameId = generateAccessCode();
        game.setGameId(gameId);

        Quiz quiz = quizService.getQuizById(game.getQuizId());
        QuizDto quizDto = new QuizDto(quiz.getId(), quiz.getName(), quiz.getQuestionNumber(),
                quiz.getImageId(), quiz.getImage(), quiz.getQuestions());
        saveQuiz(game, quizDto);
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

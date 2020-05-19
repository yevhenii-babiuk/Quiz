package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.GameDaoImpl;
import com.qucat.quiz.repositories.dto.game.AnswerDto;
import com.qucat.quiz.repositories.dto.game.GameDto;
import com.qucat.quiz.repositories.dto.game.GameQuestionDto;
import com.qucat.quiz.repositories.dto.game.QuizDto;
import com.qucat.quiz.repositories.dto.game.UserDto;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.QuestionOption;
import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
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
    private WebSocketSenderService socketSenderService;

    @Value("${url}")
    private String URL;

    @Value("${userName.animals}")
    private List<String> animals;

    @Value("${userName.characteristic}")
    private List<String> characteristic;

    int currAnimal=0;

    int currCharacteristic=0;

    private synchronized String getNewName(){
        currAnimal++;
        currCharacteristic++;
        if (animals.size() == currAnimal) {
            currAnimal=0;
        }
        if (characteristic.size() == currCharacteristic) {
            currCharacteristic=0;
        }
        return characteristic.get(currCharacteristic) + " " + animals.get(currAnimal);
    }

    public UserDto connectUser(String gameId, int userId) {
        UserDto user;
        if (userId != 0) {
            User registerUser = userService.getUserDataById(userId);
            user = UserDto.builder()
                    .login(registerUser.getLogin())
                    .registerId(registerUser.getId())
                    .gameId(gameId)
                    .build();
        } else {
            user = UserDto.builder()
                    .gameId(gameId)
                    .login(getNewName())
                    .registerId(userId)
                    .build();
        }
        user.setId(gameDao.saveUser(user));
        socketSenderService.sendUsers(user.getGameId(), gameDao.getUsersByGame(user.getGameId()));
        return user;
    }

    public void startGame(String gameId) {
        GameProcess gameProcess = new GameProcess();
        gameProcess.setGameDao(gameDao);
        gameProcess.setSocketSenderService(socketSenderService);
        gameProcess.setGameId(gameId);
        gameProcess.run();
    }


    private void calculateCorrectForAnswer(AnswerDto answer, Question question) {
        log.info("answer input" + answer);
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
                /*log.info("options " + question.getOptions().toString());
                log.info("chosenAnswers " + chosenAnswers.toString());*/
                for (QuestionOption option : question.getOptions()) {
                    if (option.isCorrect() == chosenAnswers.contains(option.getId())) {
                        correctAnswer++;
                    }
                }
                answer.setCorrect(correctAnswer == question.getOptions().size());
                answer.setPercent(100 * correctAnswer / question.getOptions().size());
                break;

            case SELECT_SEQUENCE:
                Map<Integer, Integer> sequence = answer.getSequence();
                log.info("sequence" + sequence.toString());
                log.info(" question.getOptions()" + question.getOptions());
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
        log.info("answer " + answer);
    }

    public void setAnswer(AnswerDto answer) {
        String gameID = answer.getGameId();
        answer.setTime(new Timestamp(new Date().getTime()));
        GameQuestionDto gameQuestionDto = gameDao.getCurrentQuestionByGameId(gameID);
        Question question = gameDao.getQuestionById(gameQuestionDto.getQuestionId());
        calculateCorrectForAnswer(answer, question);
        gameDao.saveAnswer(answer);
    }

    public GameDto getGameById(String gameID) {
        GameDto gameDto = gameDao.getGame(gameID);
        gameDto.setImage(getQRCode(gameDto.getQuizId(), gameID));
        return gameDto;
    }


    public Question getCurrentQuestion(String gameId, int userId) {
        GameQuestionDto gameQuestionDto = gameDao.getCurrentQuestionByGameId(gameId);
        if (gameQuestionDto == null || gameQuestionDto.getQuestionId() == 0) {
            return null;
        }
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

        for (Question question : quizDto.getQuestions()) {
            gameDao.saveGameQuestion(game.getGameId(), question.getId());//todo save gameQuestions
        }

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

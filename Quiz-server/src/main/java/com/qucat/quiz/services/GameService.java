package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.GameDao;
import com.qucat.quiz.repositories.dto.game.*;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.QuestionOption;
import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class GameService {

    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @Autowired
    private GameDao gameDao;

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

    private int currAnimal = 0;
    private int currCharacteristic = 0;


    private synchronized String getNewName() {
        currAnimal++;
        currCharacteristic++;
        if (animals.size() == currAnimal) {
            currAnimal = 0;
        }
        if (characteristic.size() == currCharacteristic) {
            currCharacteristic = 0;
        }
        return characteristic.get(currCharacteristic) + " " + animals.get(currAnimal);
    }

    public List<String> getUsersByGameId(String gameId) {
        return gameDao.getUsersByGame(gameId).stream()
                .map(UserDto::getLogin)
                .collect(Collectors.toList());
    }

    //todo create test Alexandra
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

    @Lookup
    public GameProcess createGameProcess() {
        return null;
    }

    public void startGame(String gameId) {
        GameProcess gameProcess = createGameProcess();
        gameProcess.setGameId(gameId);
        gameProcess.run();
    }

    private void calculateCorrectForAnswer(AnswerDto answer, Question question) {
        log.info("answer input" + answer);
        List<QuestionOption> options = question.getOptions();
        int correctAnswer = 0;
        switch (question.getType()) {
            case ENTER_ANSWER:
                answer.setPercent(options.get(0).getContent().trim()
                        .equalsIgnoreCase(answer.getFullAnswer().trim()) ? 100 : 0);
                break;
            case TRUE_FALSE:
                answer.setPercent(options.get(0).isCorrect() == answer.isTrueFalse() ? 100 : 0);
                break;

            case SELECT_OPTION:
                List<Integer> chosenAnswers = answer.getOptions();
                for (QuestionOption option : question.getOptions()) {
                    if (option.isCorrect() == chosenAnswers.contains(option.getId())) {
                        correctAnswer++;
                    }
                }
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
        gameDto.setCountQuestions(gameDao.getCountGameQuestion(gameID));
        return gameDto;
    }


    public Question getCurrentQuestion(String gameId, int userId) {
        GameQuestionDto gameQuestionDto = gameDao.getCurrentQuestionByGameId(gameId);
        if (gameQuestionDto == null || gameQuestionDto.getQuestionId() == 0) {
            return null;
        }
        return gameDao.getQuestionById(gameQuestionDto.getQuestionId());
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
            gameDao.saveGameQuestion(game.getGameId(), question.getId());
        }

    }


    public String createRoom(GameDto game) {
        String gameId = generateAccessCode();
        game.setGameId(gameId);

        Quiz quiz = quizService.getQuizById(game.getQuizId());
        QuizDto quizDto = new QuizDto(
                quiz.getId(),
                quiz.getName(),
                quiz.getQuestionNumber(),
                quiz.getImageId(),
                quiz.getImage(),
                quiz.getQuestions());
        saveQuiz(game, quizDto);
        return gameId;
    }

    private String getQRCode(int quizId, String accessCode) {
        return Base64.getEncoder().encodeToString(qrCodeGenerator.getQRCodeImage(
                URL + "quiz/" + quizId + "/game/" + accessCode + "/play",
                200, 200));
    }

}

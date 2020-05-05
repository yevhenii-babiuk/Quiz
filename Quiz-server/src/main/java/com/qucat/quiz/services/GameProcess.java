package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.GameDao;
import com.qucat.quiz.repositories.dto.quizplay.*;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.QuestionOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class GameProcess implements Runnable {

    private final String gameId;

    private final int COMBO_COUNT = 3;

    @Autowired
    private WebSocketSenderService socketSenderService;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private QuizService quizService;

    public GameProcess(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public void run() {
        GameDto gameDto = gameDao.getGame(gameId);

        while (gameDao.getCountGameQuestion(gameId) != 0) {
            int count = gameDao.getCountGameQuestion(gameId);
            GameQuestionDto questionDto = gameDao.getGameQuestion(gameId, (int) (Math.random() * count));
            Question question = gameDao.getQuestionById(questionDto.getQuestionId());
            gameDao.updateGameQuestionToCurrent(questionDto.getId());
            int secondLeft = gameDto.getTime();

            if (gameDto.isQuestionAnswerSequence()) {
                question.setOptions(null);
                socketSenderService.sendQuestion(question, gameDto.getGameId());
                try {
                    wait((secondLeft / 2) * 1000);
                    secondLeft -= secondLeft / 2;
                } catch (InterruptedException e) {
                    log.error("Thread was interrupted", e);
                }
                question = gameDao.getQuestionById(questionDto.getQuestionId());
            }

            clearRightAnswer(question);

            socketSenderService.sendQuestion(question, gameDto.getGameId());

            while (!checkAllSendAnswer() || secondLeft != -1) {
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    log.error("Thread was interrupted", e);
                }
                secondLeft--;
            }

            question = gameDao.getQuestionById(questionDto.getQuestionId());
            List<AnswerDto> answers = gameDao.getAnswersToCurrentQuestionByGameId(gameId);

            for (AnswerDto answer : answers) {
                answer.setCorrect(false);
                UserDto user = answer.getUser();
                String answerStr = answer.getAnswer();
                answerStr = answerStr.substring(answerStr.indexOf(':'));

                switch (question.getType()) {
                    case ENTER_ANSWER: {
                        List<QuestionOption> options = question.getOptions();
                        answer.setCorrect(options.get(0).getContent().toLowerCase().trim()
                                .equals(answerStr.toLowerCase().trim()));
                        if (answer.isCorrect()) {
                            user.setScore(user.getScore() + question.getScore());
                        }
                        break;
                    }
                    case TRUE_FALSE: {
                        List<QuestionOption> options = question.getOptions();
                        answer.setCorrect(options.get(0).isCorrect() == answerStr.equals("true"));
                        if (answer.isCorrect()) {
                            user.setScore(user.getScore() + question.getScore());
                        }
                        break;
                    }
                    case SELECT_OPTION: {
                        String[] userAnswers = answerStr.split(" ");
                        List<Integer> chosenAnswers = new ArrayList<>();
                        for (String currAnswer : userAnswers) {
                            chosenAnswers.add(Integer.parseInt(currAnswer));
                        }
                        int correctAnswer = 0;
                        for (QuestionOption option : question.getOptions()) {
                            if (option.isCorrect() == chosenAnswers.contains(option.getId())) {
                                correctAnswer++;
                            }
                        }
                        if (correctAnswer == question.getOptions().size()) {
                            answer.setCorrect(true);
                        }
                        user.setScore(user.getScore() + question.getScore() * correctAnswer / question.getOptions().size());
                        break;
                    }
                    case SELECT_SEQUENCE: {
                        String[] currAnswers = answerStr.split(" ");
                        HashMap<Integer, Integer> userAnswers = new HashMap<>();
                        for (String currAnswer : currAnswers) {
                            String[] oneUserAnswer = currAnswer.split("-");
                            userAnswers.put(Integer.parseInt(oneUserAnswer[0]), Integer.parseInt(oneUserAnswer[1]));
                        }
                        int correctAnswer = 0;
                        for (QuestionOption option : question.getOptions()) {
                            if (option.getSequenceOrder() == userAnswers.get(option.getId())) {
                                correctAnswer++;
                            }
                        }
                        if (correctAnswer == question.getOptions().size()) {
                            answer.setCorrect(true);
                        }
                        user.setScore(user.getScore() + question.getScore() * correctAnswer / question.getOptions().size());

                        break;
                    }
                    default:
                        break;
                }

                if (gameDto.isCombo() && answer.isCorrect()) {
                    user.setComboAnswer(user.getComboAnswer() + 1);
                    if (user.getComboAnswer() >= COMBO_COUNT) {
                        user.setScore(user.getScore() + question.getScore() * (user.getComboAnswer() - COMBO_COUNT + 1));
                    }
                } else {
                    user.setComboAnswer(0);
                }

                gameDao.updateUserDto(user);
            }

            if (gameDto.isQuickAnswerBonus()) {
                int minTime = Integer.MAX_VALUE;
                for (AnswerDto answer : answers) {
                    if (answer.isCorrect()) {
                        minTime = Integer.min(minTime, answer.getTime());
                    }
                }
                for (AnswerDto answer : answers) {
                    if (answer.getTime() == minTime) {
                        UserDto user = answer.getUser();
                        user.setScore(user.getScore() + answer.getQuestion().getScore());
                        gameDao.updateUserDto(user);
                    }
                }
            }

            if (gameDto.isIntermediateResult()) {
                Users users = Users.builder().users(gameDao.getUsersByGame(gameId)).build();
                socketSenderService.sendUsers(users, gameId);
                try {
                    wait((long) 1e4);
                } catch (InterruptedException e) {
                    log.error("Thread was interrupted", e);
                }
            }

            gameDao.deleteGameQuestion(questionDto.getId());
        }
        //todo send results, and send end command
        gameDao.deleteGame(gameId);
    }

    private void clearRightAnswer(Question question) {
        switch (question.getType()) {
            case ENTER_ANSWER: {
                question.getOptions().get(0).setContent(null);
                break;
            }
            case TRUE_FALSE: {
                question.getOptions().get(0).setCorrect(false);
                break;
            }
            case SELECT_OPTION: {
                for (QuestionOption option : question.getOptions()) {
                    option.setCorrect(false);
                }
                break;
            }
            case SELECT_SEQUENCE: {
                for (QuestionOption option : question.getOptions()) {
                    option.setSequenceOrder(0);
                }
                break;
            }
            default:
                break;
        }
    }


    private boolean checkAllSendAnswer() {
        List<AnswerDto> answers = gameDao.getAnswersToCurrentQuestionByGameId(gameId);
        List<UserDto> users = gameDao.getUsersByGame(gameId);

        return answers.size() == users.size();
    }


}

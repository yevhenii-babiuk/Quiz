package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.GameDao;
import com.qucat.quiz.repositories.dto.*;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.QuestionOption;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Data
@Scope("prototype")
public class GameProcess implements Runnable {

    private final int COMBO_COUNT = 3;

    private String gameId;

    @Autowired
    private WebSocketSenderService socketSenderService;

    @Autowired
    private GameDao gameDao;


    private void questionAnswerSequence(Question question, int secondLeft) {
        socketSenderService.sendQuestion(question, gameId);
        try {
            Thread.sleep((secondLeft / 2) * 1000);
        } catch (InterruptedException e) {
            log.error("Thread was interrupted", e);
        }
    }

    private void waitAllAnswer(int secondLeft) {
        while (!checkAllSendAnswer() && secondLeft != -1) {
            try {
                Thread.sleep(1000);
                secondLeft--;
            } catch (InterruptedException e) {
                log.error("Thread was interrupted", e);
            }
        }
    }

    private void sendResults() {
        Users users = new Users(gameDao.getUsersByGame(gameId));
        socketSenderService.sendResults(gameId, users);
        try {
            Thread.sleep((long) 1e4);
        } catch (InterruptedException e) {
            log.error("Thread was interrupted", e);
        }
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
                questionAnswerSequence(question, secondLeft);
                secondLeft -= secondLeft / 2;
                question = gameDao.getQuestionById(questionDto.getQuestionId());
            }

            clearRightAnswer(question);
            socketSenderService.sendQuestion(question, gameDto.getGameId());

            waitAllAnswer(secondLeft);


            question = gameDao.getQuestionById(questionDto.getQuestionId());
            List<AnswerDto> answers = gameDao.getAnswersToCurrentQuestionByGameId(gameId);
            for (AnswerDto answer : answers) {
                UserDto user = answer.getUser();
                user.setScore(user.getScore() + question.getScore() * answer.getPercent() / 100);

                if (gameDto.isCombo() && answer.getPercent() == 100) {
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
                AnswerDto firstCorrectAnswer = answers.stream()//todo
                        .filter(answerDto -> answerDto.getPercent() == 100)
                        .findFirst()
                        .orElse(AnswerDto.builder().build());
                if (firstCorrectAnswer.getPercent() == 100) {
                    UserDto user = firstCorrectAnswer.getUser();
                    user.setScore(user.getScore() + firstCorrectAnswer.getQuestion().getScore());
                    gameDao.updateUserDto(user);
                }
            }

            if (gameDto.isIntermediateResult() && count != 1) {
                sendResults();
            }

            gameDao.deleteGameQuestion(questionDto.getId());
        }
        sendResults();
        //todo send end command
        //todo save
        gameDao.deleteGame(gameId);
    }

    private void clearRightAnswer(Question question) {
        switch (question.getType()) {
            case ENTER_ANSWER:
                question.getOptions().get(0).setContent(null);
                break;

            case TRUE_FALSE:
                question.getOptions().get(0).setCorrect(false);
                break;

            case SELECT_OPTION:
                for (QuestionOption option : question.getOptions()) {
                    option.setCorrect(false);
                }
                break;

            case SELECT_SEQUENCE:
                for (QuestionOption option : question.getOptions()) {
                    option.setSequenceOrder(0);
                }
                break;

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

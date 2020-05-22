package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.TakeQuizDao;
import com.qucat.quiz.repositories.dto.game.UserDto;
import com.qucat.quiz.repositories.entities.TakeQuiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TakeQuizService {

    @Autowired
    private TakeQuizDao takeQuizDao;

    @Autowired
    private UserService userService;

    public void saveUsersResults(List<UserDto> users) {
        for (UserDto user : users) {
            if (user.getRegisterId() == 0) {
                continue;
            }
            TakeQuiz takeQuiz = takeQuizDao.getUserResultByQuiz(
                    user.getRegisterId(), user.getQuizId());
            if (takeQuiz != null && user.getScore() > takeQuiz.getScore()) {
                takeQuizDao.update(createNewTakeQuiz(user));
            } else if (takeQuiz == null) {
                takeQuizDao.save(createNewTakeQuiz(user));
            }
            userService.updateUsersScore(user);
        }
    }

    private TakeQuiz createNewTakeQuiz(UserDto user) {
        return TakeQuiz.builder()
                .userId(user.getRegisterId())
                .score(user.getScore())
                .quizId(user.getQuizId())
                .isCompleted(true)
                .build();
    }
}

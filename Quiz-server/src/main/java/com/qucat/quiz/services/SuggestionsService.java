package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.SuggestionDao;
import com.qucat.quiz.repositories.entities.enums.Lang;
import com.qucat.quiz.repositories.entities.enums.MessageInfo;
import com.qucat.quiz.utils.SuggestionsMailingThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@PropertySource("classpath:mail/application-mail-config.properties")
public class SuggestionsService {
    @Autowired
    private EmailSender emailSender;

    @Autowired
    private SuggestionDao suggestionDao;

    @Autowired
    private UserService userService;

    @Value("${url}")
    private String URL;

    public void sendSuggestion(int quizId, String quizName, String categoryName) {
        Map<String, String> users = suggestionDao.getLoginAndEmail(quizId);
        if (users.isEmpty()) {
            log.warn("There are no users to recommend quiz with id={}", quizId);
        } else {
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (Map.Entry<String, String> entry : users.entrySet()) {
                Lang userLanguage = userService.getUserLanguageByLogin(entry.getKey());
                executorService.execute(SuggestionsMailingThread.builder()
                        .emailSender(emailSender)
                        .login(entry.getKey())
                        .email(entry.getValue())
                        .URL(URL)
                        .quizName(quizName)
                        .categoryName(categoryName)
                        .quizId(Integer.toString(quizId))
                        .messageInfoItem(MessageInfo.suggestion.findByLang(userLanguage))
                        .build());
            }
            executorService.shutdown();
        }
    }
}

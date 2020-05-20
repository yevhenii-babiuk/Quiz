package com.qucat.quiz.repositories.dao;

import java.util.Map;

public interface SuggestionDao {
    Map<String, String> getLoginAndEmail(int quizId);
}

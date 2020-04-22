package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Tag;

import java.util.List;

public interface TagDao extends GenericDao<Tag> {
    String TABLE_NAME = "tag";

    List<Tag> getByQuizId(int id);

    int getIdByName(String name);
}

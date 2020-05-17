package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Notification;

import java.util.List;

public interface NotificationDao extends GenericDao<Notification> {
    String TABLE_NAME = "notification";

    List<Notification> getByUserId(int userId);

    void deleteAllByUserId(int id);

}

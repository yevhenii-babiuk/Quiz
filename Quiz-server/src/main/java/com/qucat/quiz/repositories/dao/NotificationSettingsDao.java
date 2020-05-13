package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Notification;
import com.qucat.quiz.repositories.entities.NotificationSettings;

import java.util.List;

public interface NotificationSettingsDao extends GenericDao<NotificationSettings> {
    String TABLE_NAME = "notification_settings";

    List<Notification> getByUserId(int id);
}

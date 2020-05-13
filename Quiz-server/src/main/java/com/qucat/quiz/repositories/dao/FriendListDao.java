package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.NotificationType;

import java.util.List;

public interface FriendListDao {

    List<Integer> getForNotification(int id, NotificationType notificationType);

}

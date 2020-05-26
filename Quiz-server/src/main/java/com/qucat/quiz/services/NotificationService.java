package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.NotificationDao;
import com.qucat.quiz.repositories.entities.Notification;
import com.qucat.quiz.repositories.entities.NotificationType;
import com.qucat.quiz.repositories.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NotificationService {
    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private UserService userService;

    //todo create test Dima
    public Notification generateNotification(int authorId, int objectId, int userId, NotificationType notificationType) {
        User notificationAuthor = userService.getUserDataById(authorId);

        Notification notification = Notification.builder()
                .isViewed(false)
                .author(notificationAuthor.getLogin())
                .authorLink("users/" + authorId)
                .userId(userId)
                .isMessage(false)
                .build();
        switch (notificationType) {
            case CREATED_NEWS:
                notification.setAction("CREATED_NEWS");
                notification.setActionLink("announcement/" + objectId);
                break;
            case CREATED_QUIZ:
                notification.setAction("CREATED_QUIZ");
                notification.setActionLink("quiz/" + objectId);
                break;
            case GAME_INVITATION:
                notification.setAction("GAME_INVITATION");
                notification.setActionLink("quiz/35/game/null/play" + objectId);
                break;
            case FRIEND_INVITATION:
                notification.setAction("FRIEND_INVITATION");
                notification.setActionLink("profile/" + authorId);
                notification.setUserId(objectId);
                break;
            case MESSAGE:
                notification.setAction("MESSAGE");
                notification.setActionLink("chat/" + objectId);
                notification.setMessage(true);
                break;
            default:
                return null;
        }
        createNotification(notification);
        return notification;
    }

    public boolean createNotification(Notification notification) {
        int notificationId = notificationDao.save(notification);
        if (notificationId == -1) {
            log.info("createNotification: Notification wasn't saved");
            return false;
        }
        return true;
    }

    @Scheduled(cron = "**14**")
    private void deleteOldNotifications() {
        notificationDao.deleteOldNotifications();
    }

    public void updateNotification(Notification notification) {
        if (notification == null) {
            log.info("updateNotification: Notification is null");
            return;
        }
        notificationDao.update(notification);
    }

    public void deleteNotificationById(int id) {
        notificationDao.deleteById(id);
    }

    public Notification getNotificationById(int id) {
        return notificationDao.get(id);
    }

    public List<Notification> getNotificationsByUserId(int userId) {
        List<Notification> list = notificationDao.getByUserId(userId);
        return list;
    }

    public List<Notification> getMessagesByUserId(int userId) {
        List<Notification> list = notificationDao.getMessagesByUserId(userId);
        return list;
    }

    public void deleteAllUserNotifications(int userId) {
        notificationDao.deleteAllByUserId(userId);
    }
}

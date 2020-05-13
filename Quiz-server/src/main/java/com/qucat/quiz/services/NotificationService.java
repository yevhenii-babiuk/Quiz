package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.NotificationDao;
import com.qucat.quiz.repositories.entities.Notification;
import com.qucat.quiz.repositories.entities.NotificationType;
import com.qucat.quiz.repositories.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NotificationService {
    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private UserService userService;

    public Notification generateNotification(int authorId, int objectId, NotificationType notificationType) {
        User notificationAuthor = userService.getUserDataById(authorId);

        Notification notification = new Notification();
        notification.setIsViewed(false);
        notification.setAuthor(notificationAuthor.getLogin());
        notification.setAuthorLink("/profile");//todo change link on author
        switch (notificationType) {
            case CREATED_NEWS:
                notification.setAction("створив новину!");
                notification.setActionLink("/announcement/" + objectId);
                break;
            case CREATED_QUIZ:
                notification.setAction("створив вікторину, поспішай збирати команду!");
                notification.setActionLink("/quiz/" + objectId);
                break;
            case GAME_INVITATION:
                notification.setAction("запрошує Вас у гру, впред за новими рекордами!");
                notification.setActionLink("/quiz/35/game/null/play" + objectId);
                break;
            case FRIEND_INVITATION:
                notification.setAction("запрошує Вас у друзі!");
                notification.setActionLink("http://localhost:4200/?#/" + objectId);
                break;
            default:
                return null;
        }
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

    public void updateNotification(Notification notification) {
        if (notification == null) {
            log.info("updateNotification: Notification is null");
            return;
        }
        notificationDao.update(notification);
    }

    public void deleteNotification(Notification notification) {
        notificationDao.delete(notification);
    }

    public void deleteNotificationById(int id) {
        notificationDao.deleteById(id);
    }

    public Notification getNotificationById(int id) {
        return notificationDao.get(id);
    }

    public List<Notification> getNotificationsByUserId(int userId) {
        return notificationDao.getByUserId(userId);
    }

}

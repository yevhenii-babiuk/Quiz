package com.qucat.quiz.services;


import com.qucat.quiz.repositories.dao.NotificationDao;
import com.qucat.quiz.repositories.entities.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class NotificationService {

    @Autowired
    private NotificationDao notificationDao;

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

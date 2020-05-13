package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Notification;
import com.qucat.quiz.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PutMapping("/notifications")
    public List<Notification> getNotifications(@RequestParam int userId) {
        return notificationService.getNotificationsByUserId(userId);
    }

}

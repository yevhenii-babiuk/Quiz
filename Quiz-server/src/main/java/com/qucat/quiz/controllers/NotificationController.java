package com.qucat.quiz.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/notify")
    public void onReceiveNotification(String message) {
       // Gson g = new Gson();
        //String answer = g.fromJson(message, String.class);
        System.out.println("message: " );

      this.template.convertAndSend("/event","new notification" );

    }


}

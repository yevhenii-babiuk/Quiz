package com.qucat.quiz.controllers;

import com.google.gson.Gson;
import com.qucat.quiz.repositories.dto.WebsocketEvent;
import com.qucat.quiz.repositories.entities.Chat;
import com.qucat.quiz.repositories.entities.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class ChatController {
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/{chatId}")
    public void receiveMessage(@DestinationVariable String chatId, String message) {
        Gson gson = new Gson();
        Message msg = gson.fromJson(message, Message.class);

        msg.setAuthorLogin("sid");
        msg.setCreationDate(new Date());

        System.out.println(msg);

        this.template.convertAndSend(String.format("/chat/%s", chatId),
                gson.toJson(WebsocketEvent.builder().type(WebsocketEvent.EventType.MESSAGE).message(msg).build()));
    }

    @GetMapping("api/v1/users/{userId}/chats")
    public List<Chat> getGameById(@PathVariable String userId) {

        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat(1, "Test Chat1", new Date()));
        chats.add(new Chat(2, "Test Chat2", new Date()));
        chats.add(new Chat(3, "Test Chat3", new Date()));
        chats.add(new Chat(4, "Test Chat4", new Date()));
        chats.add(new Chat(5, "Test Chat5", new Date()));
        chats.add(new Chat(6, "Test Chat6", new Date()));

        return chats;
    }

    @GetMapping("api/v1/chat/{chatId}")
    public Chat getChatById(@PathVariable int chatId) {

        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat(1, "Test Chat1", new Date()));
        chats.add(new Chat(2, "Test Chat2", new Date()));
        chats.add(new Chat(3, "Test Chat3", new Date()));
        chats.add(new Chat(4, "Test Chat4", new Date()));
        chats.add(new Chat(5, "Test Chat5", new Date()));
        chats.add(new Chat(6, "Test Chat6", new Date()));

        return chats.get(chatId - 1);
    }

    @GetMapping("api/v1/chat/{chatId}/messages")
    public List<Message> getChatMessages(@PathVariable int chatId) {

        List<Message> messages = new ArrayList<>();
        messages.add(
                new Message(0, chatId,11, "first", new Date(), true,
             "11-0We work directly with our designers and suppliers,\n" +
               "            and sell direct to you, which means quality, exclusive\n" +
               "            products, at a price anyone can afford. We work directly with our designers and suppliers,\n" +
               "            and sell direct to you, which means quality, exclusive\n" +
               "            products, at a price anyone can afford.")
        );
        messages.add(
                new Message(1, chatId,11, "first", new Date(), true,
                        "12-6We work directly with our designers and suppliers,\n" +
                        "            and sell direct to you, which means quality, exclusive\n" +
                                "            products, at a price anyone can afford.")
        );
        messages.add(
                new Message(2, chatId,83, "sid", new Date(), true,
                        "11-0We work directly with our designers and suppliers,\n" +
                                "            and sell direct to you, which means quality, exclusive\n" +
                                "            products, at a price anyone can afford. We work directly with our designers and suppliers,\n" +
                                "            and sell direct to you, which means quality, exclusive\n" +
                                "            products, at a price anyone can afford.")
        );
        messages.add(
                new Message(3, chatId,11, "first", new Date(), true,
                        "11-0We work directly with our designers and suppliers,\n" +
                                "            and sell direct to you, which means quality, exclusive\n" +
                                "            products, at a price anyone can afford. We work directly with our designers and suppliers,\n" +
                                "            and sell direct to you, which means quality, exclusive\n" +
                                "            products, at a price anyone can afford.")
        );

        return messages;
    }

    @PutMapping("api/v1/chat")
    public void updateChat(@RequestBody Chat chat) {
        System.out.println(chat);
        //todo update chat
    }

    @PostMapping("api/v1/users/{id}/createChat")
    public boolean createChat(@PathVariable String id) {
        System.out.println("user " + id + " created chat");
        //todo create chat
        return true;
    }

    @DeleteMapping("api/v1/users/{id}/chat{chatId}")
    public void leaveChat(@PathVariable String id, @PathVariable String chatId) {
        System.out.println("user " + id + " leaved chat " + chatId);
        //todo leave chat
    }
}

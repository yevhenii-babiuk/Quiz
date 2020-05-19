package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Chat;
import com.qucat.quiz.services.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("api/v1/users/{userId}/chats")
    public List<Chat> getUserChats(@PathVariable int userId) {
        return chatService.getAllChatsForUser(userId);
    }

    @GetMapping("api/v1/chat/{chatId}")
    public Chat getChatById(@PathVariable int chatId) {
        return chatService.getFullChatInfo(chatId);
    }

    @PostMapping("api/v1/users/{id}/chat/{chatId}/invite")
    public void addMemberToChat(@PathVariable int id, @PathVariable int chatId) {
        chatService.addMemberToChat(chatId, id);
    }

    @PutMapping("api/v1/chat")
    public void updateChat(@RequestBody Chat chat) {
        chatService.updateChat(chat);
    }

    @PostMapping("api/v1/createChat")
    public int createChat(@RequestBody Chat chat) {
        return chatService.createChat(chat);
    }

    @DeleteMapping("api/v1/users/{id}/chat/{chatId}")
    public void leaveChat(@PathVariable int id, @PathVariable int chatId) {
        chatService.removeMemberFromChat(chatId, id);
    }
}

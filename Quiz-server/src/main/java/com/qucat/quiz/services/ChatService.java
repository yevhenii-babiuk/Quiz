package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.ChatDao;
import com.qucat.quiz.repositories.entities.Chat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatDao chatDao;

    public List<Chat> getAllChatsForUser(int userId) {
        return chatDao.getChatsForUser(userId);
    }

    public Chat getFullChatInfo(int chatId) {
        return chatDao.getFullInfo(chatId);
    }

    public void addMemberToChat(int chatId, int userId) {
        chatDao.addChatMember(chatId, userId);
    }

    public void removeMemberFromChat(int chatId, int userId) {
        chatDao.removeChatMember(chatId, userId);
    }

}

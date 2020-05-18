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
        if (userId == 0) {
            log.warn("User id can`t be 0");
            throw new IllegalArgumentException("0 can`t be user`s identifier");
        }
        return chatDao.getChatsForUser(userId);
    }

    public Chat getFullChatInfo(int chatId) {
        if (chatId == 0) {
            log.warn("Chat id can`t be 0");
            throw new IllegalArgumentException("0 can`t be chat`s identifier");
        }
        return chatDao.getFullInfo(chatId);
    }

    public void addMemberToChat(int chatId, int userId) {
        if (chatId == 0) {
            log.warn("Chat id can`t be 0");
            throw new IllegalArgumentException("0 can`t be chat`s identifier");
        }
        if (userId == 0) {
            log.warn("User id can`t be 0");
            throw new IllegalArgumentException("0 can`t be user`s identifier");
        }
        chatDao.addChatMember(chatId, userId);
    }

    public void removeMemberFromChat(int chatId, int userId) {
        if (chatId == 0) {
            log.warn("Chat id can`t be 0");
            throw new IllegalArgumentException("0 can`t be chat`s identifier");
        }
        if (userId == 0) {
            log.warn("User id can`t be 0");
            throw new IllegalArgumentException("0 can`t be user`s identifier");
        }
        chatDao.removeChatMember(chatId, userId);
    }

    public int createChat(Chat chat) {
        return chatDao.save(chat);
    }

    public void updateChat(Chat chat) {
        chatDao.update(chat);
    }
}

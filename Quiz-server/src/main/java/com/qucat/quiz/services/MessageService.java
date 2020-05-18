package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.MessageDao;
import com.qucat.quiz.repositories.entities.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public boolean saveMessage(Message message) {
        int messageId = messageDao.save(message);
        return messageId > 0;
    }

    public Page<Message> getPageMessages(int chatId, Optional<Integer> page, Optional<Integer> size) {
        if (chatId == 0) {
            log.warn("Chat id can`t be 0");
            throw new IllegalArgumentException("0 can`t be chat`s identifier");
        }
        return messageDao.getMessagesFromChat(chatId, PageRequest.of(page.orElse(0), size.orElse(10),
                Sort.Direction.DESC, "creationDate"));
    }
}

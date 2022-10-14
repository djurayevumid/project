package com.javamentor.qa.platform.dao.abstracts.model.chat;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.Message;

import java.util.List;

public interface MessageDao extends ReadWriteDao<Message, Long> {
    List<Message> getMessagesFromChat(Long chatId);
}

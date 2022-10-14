package com.javamentor.qa.platform.service.abstracts.model.chat;

import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

import java.util.List;

public interface MessageService extends ReadWriteService<Message, Long> {
    List<Message> getMessagesFromChat(Long chatId);
}

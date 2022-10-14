package com.javamentor.qa.platform.service.impl.model.chat;

import com.javamentor.qa.platform.dao.abstracts.model.chat.MessageDao;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.service.abstracts.model.chat.MessageService;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl extends ReadWriteServiceImpl<Message, Long> implements MessageService {
    private final MessageDao messageDao;

    public MessageServiceImpl(MessageDao messageDao) {
        super(messageDao);
        this.messageDao = messageDao;
    }

    @Override
    public List<Message> getMessagesFromChat(Long chatId) {
        return messageDao.getMessagesFromChat(chatId);
    }
}

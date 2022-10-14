package com.javamentor.qa.platform.service.impl.model.chat;

import com.javamentor.qa.platform.dao.abstracts.model.ReadOnlyDao;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.service.abstracts.model.chat.ChatService;
import com.javamentor.qa.platform.service.impl.model.ReadOnlyServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl extends ReadOnlyServiceImpl<Chat, Long> implements ChatService {
    public ChatServiceImpl(ReadOnlyDao<Chat, Long> readOnlyDao) {
        super(readOnlyDao);
    }
}
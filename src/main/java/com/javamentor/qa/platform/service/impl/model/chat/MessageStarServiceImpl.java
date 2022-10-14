package com.javamentor.qa.platform.service.impl.model.chat;


import com.javamentor.qa.platform.dao.abstracts.model.chat.MessageStarDao;
import com.javamentor.qa.platform.models.entity.chat.MessageStar;
import com.javamentor.qa.platform.service.abstracts.model.chat.MessageStarService;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MessageStarServiceImpl extends ReadWriteServiceImpl<MessageStar, Long> implements MessageStarService {

    MessageStarDao messageStarDao;
    public MessageStarServiceImpl(MessageStarDao messageStarDao) {
        super(messageStarDao);
        this.messageStarDao = messageStarDao;
    }

    @Override
    @Transactional
    public void deleteMessageStarByMessageIdUserId(Long messageId, Long userId) {
        messageStarDao.deleteMessageStarByMessageIdUserId(messageId, userId);
    }

    @Override
    @Transactional
    public boolean existsMessageStarByMessageIdUserId(Long messageId, Long userId) {
        return messageStarDao.existsMessageStarByMessageIdUserId(messageId, userId);
    }
}

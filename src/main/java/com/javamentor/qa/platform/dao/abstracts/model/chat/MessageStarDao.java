package com.javamentor.qa.platform.dao.abstracts.model.chat;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.MessageStar;

public interface MessageStarDao extends ReadWriteDao<MessageStar, Long> {

   void deleteMessageStarByMessageIdUserId(Long messageId, Long userId);

   boolean existsMessageStarByMessageIdUserId(Long messageId, Long userId);
}

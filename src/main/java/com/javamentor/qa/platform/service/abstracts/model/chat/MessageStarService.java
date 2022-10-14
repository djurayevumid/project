package com.javamentor.qa.platform.service.abstracts.model.chat;

import com.javamentor.qa.platform.models.entity.chat.MessageStar;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

public interface MessageStarService extends ReadWriteService<MessageStar, Long> {
    void deleteMessageStarByMessageIdUserId(Long messageId, Long userId);

    boolean existsMessageStarByMessageIdUserId(Long messageId, Long userId);
}

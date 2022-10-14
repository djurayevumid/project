package com.javamentor.qa.platform.service.abstracts.model.chat;

import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

public interface SingleChatService extends ReadWriteService<SingleChat, Long> {
    void addSingleChatAndMessage(SingleChat singleChat, String message);

    void markSingleChatAsDelForUser(Long chatId);

    boolean ifUserInSingleChat(Long userId, Long chatId);
}

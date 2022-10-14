package com.javamentor.qa.platform.service.abstracts.model.chat;

import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

public interface GroupChatService extends ReadWriteService<GroupChat, Long> {
    void delUserFromGroupChat(Long chatId);
    boolean ifUserInGroupChat(Long chatId, Long userId);
}

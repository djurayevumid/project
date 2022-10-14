package com.javamentor.qa.platform.dao.abstracts.model.chat;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;

public interface GroupChatDao extends ReadWriteDao<GroupChat, Long> {
    void delUserFromGroupChat(Long chatId, Long userId);
    boolean checkIfUserIsInChat(Long chatId, Long userId);
}

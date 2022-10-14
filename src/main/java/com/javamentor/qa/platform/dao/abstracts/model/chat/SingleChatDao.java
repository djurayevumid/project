package com.javamentor.qa.platform.dao.abstracts.model.chat;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.Optional;

public interface SingleChatDao extends ReadWriteDao<SingleChat, Long> {
    Optional<SingleChat> getSingleChatByUserOneAndUserTwo(long firstUser, long secondUser);
    void deleteChatForUser(Long chatId, Long userId);
    boolean checkIsAlreadyDeleted(Long chatId, Long userId);
    boolean checkUserInSingleChat(Long chatId, Long userId);
}

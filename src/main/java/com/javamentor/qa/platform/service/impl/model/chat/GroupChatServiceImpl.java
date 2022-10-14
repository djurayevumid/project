package com.javamentor.qa.platform.service.impl.model.chat;

import com.javamentor.qa.platform.dao.abstracts.model.chat.GroupChatDao;
import com.javamentor.qa.platform.exception.ChatBadRequestException;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.chat.GroupChatService;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GroupChatServiceImpl extends ReadWriteServiceImpl<GroupChat, Long> implements GroupChatService {

    private final GroupChatDao groupChatDao;


    public GroupChatServiceImpl(GroupChatDao groupChatDao) {
        super(groupChatDao);
        this.groupChatDao = groupChatDao;
    }

    @Transactional
    @Override
    public void delUserFromGroupChat(Long chatId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (groupChatDao.checkIfUserIsInChat(chatId, user.getId())) {
            groupChatDao.delUserFromGroupChat(chatId, user.getId());
        } else {
            throw new ChatBadRequestException("There is no chat with this id in the database or no access");
        }
    }

    @Override
    public boolean ifUserInGroupChat(Long chatId, Long userId) {
        return groupChatDao.checkIfUserIsInChat(chatId, userId);
    }

}

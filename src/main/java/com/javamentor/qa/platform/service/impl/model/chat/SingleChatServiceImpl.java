package com.javamentor.qa.platform.service.impl.model.chat;

import com.javamentor.qa.platform.dao.abstracts.model.chat.MessageDao;
import com.javamentor.qa.platform.dao.abstracts.model.chat.SingleChatDao;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.exception.ChatAlreadyDeletedException;
import com.javamentor.qa.platform.exception.ChatBadRequestException;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.chat.SingleChatService;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SingleChatServiceImpl extends ReadWriteServiceImpl<SingleChat, Long> implements SingleChatService {
    private final MessageDao messageDao;
    private final SingleChatDao singleChatDao;

    public SingleChatServiceImpl(SingleChatDao singleChatDao, MessageDao messageDao, SingleChatDao singleChatDao1) {
        super(singleChatDao);
        this.messageDao = messageDao;
        this.singleChatDao = singleChatDao1;
    }

    @Override
    @Transactional
    public void addSingleChatAndMessage(SingleChat singleChat, String message) {
        Message newMessage = new Message();
        singleChat.setDelByUserOne(false);
        singleChat.setDelByUserTwo(false);
        Optional<SingleChat> optionalSingleChat = singleChatDao.getSingleChatByUserOneAndUserTwo(
                singleChat.getUserOne().getId(), singleChat.getUserTwo().getId());
        if (optionalSingleChat.isPresent()) {
            newMessage.setChat(optionalSingleChat.get().getChat());
            newMessage.setMessage(message);
            newMessage.setUserSender(singleChat.getUserOne());
            messageDao.persist(newMessage);
            return;
        }
        newMessage.setChat(singleChat.getChat());
        newMessage.setMessage(message);
        newMessage.setUserSender(singleChat.getUserOne());
        super.persist(singleChat);
        messageDao.persist(newMessage);
    }

    @Override
    @Transactional
    public void markSingleChatAsDelForUser(Long chatId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (singleChatDao.checkIsAlreadyDeleted(chatId, user.getId())) {
            throw new ChatAlreadyDeletedException("Chat is already deleted");
        }
        if (singleChatDao.checkUserInSingleChat(chatId, user.getId())) {
            singleChatDao.deleteChatForUser(chatId, user.getId());
        } else {
            throw new ChatBadRequestException("There is no chat with this id in the database or no access");
        }
    }

    @Override
    @Transactional
    public boolean ifUserInSingleChat(Long userId, Long chatId) {
        return singleChatDao.checkUserInSingleChat(chatId, userId);
    }
}

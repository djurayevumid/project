package com.javamentor.qa.platform.dao.impl.model.chat;

import com.javamentor.qa.platform.dao.abstracts.model.chat.GroupChatDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;

@Repository
public class GroupChatDaoImpl extends ReadWriteDaoImpl<GroupChat, Long> implements GroupChatDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean checkIfUserIsInChat(Long chatId, Long userId) {
        BigInteger userInChat = (BigInteger) entityManager.createNativeQuery("select count(*) from groupchat_has_users" +
                        " where chat_id =:chatId and user_id =:userId")
                .setParameter("chatId", chatId)
                .setParameter("userId", userId)
                .getSingleResult();
        return !userInChat.equals(BigInteger.ZERO);
    }

    @Override
    public void delUserFromGroupChat(Long chatId, Long userId) {
        entityManager.createNativeQuery("delete from groupchat_has_users where chat_id = :chatId and user_id = :userId")
                .setParameter("chatId", chatId)
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
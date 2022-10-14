package com.javamentor.qa.platform.dao.impl.model.chat;

import com.javamentor.qa.platform.dao.abstracts.model.chat.SingleChatDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.math.BigInteger;

@Repository
public class SingleChatDaoImpl extends ReadWriteDaoImpl<SingleChat, Long> implements SingleChatDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SingleChat> getSingleChatByUserOneAndUserTwo(long firstUser, long secondUser) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("FROM SingleChat sc " +
                                "where sc.userOne.id = :u1 and sc.userTwo.id =:u2 " +
                                "or sc.userOne.id = :u2 and sc.userTwo.id =:u1",
                        SingleChat.class).
                setParameter("u1", firstUser).
                setParameter("u2", secondUser));
    }

    @Override
    public void deleteChatForUser(Long chatId, Long userId) {
        entityManager.createNativeQuery("update single_chat set " +
                        "del_by_user_one = case when chat_id = :chatId and user_one_id = :userId then true else del_by_user_one end," +
                        "del_by_user_two = case when chat_id = :chatId and user_two_id = :userId then true else del_by_user_two end ")
                .setParameter("chatId", chatId)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public boolean checkIsAlreadyDeleted(Long chatId, Long userId) {
        BigInteger userInChat = (BigInteger) entityManager.createNativeQuery("select count(*) from single_chat" +
                        " where chat_id =:chatId and user_one_id =:userId and del_by_user_one = true" +
                        " or chat_id =:chatId and user_two_id =:userId and del_by_user_two = true")
                .setParameter("chatId", chatId)
                .setParameter("userId", userId)
                .getSingleResult();
        if (!userInChat.equals(BigInteger.ZERO)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkUserInSingleChat(Long chatId, Long userId) {
        BigInteger userInChat = (BigInteger) entityManager.createNativeQuery("select count(*) from single_chat" +
                        " where chat_id =:chatId and user_one_id =:userId" +
                        " or chat_id =:chatId and user_two_id =:userId")
                .setParameter("chatId", chatId)
                .setParameter("userId", userId)
                .getSingleResult();
        if (!userInChat.equals(BigInteger.ZERO)) {
            return true;
        }
        return false;
    }
}

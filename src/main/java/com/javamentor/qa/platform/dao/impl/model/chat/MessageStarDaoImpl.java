package com.javamentor.qa.platform.dao.impl.model.chat;

import com.javamentor.qa.platform.dao.abstracts.model.chat.MessageStarDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.chat.MessageStar;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MessageStarDaoImpl extends ReadWriteDaoImpl<MessageStar, Long> implements MessageStarDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteMessageStarByMessageIdUserId(Long messageId, Long userId) {
        entityManager.createQuery("DELETE FROM MessageStar WHERE message.id = :messageId AND " +
                "user.id = :userId")
                .setParameter("messageId", messageId)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public boolean existsMessageStarByMessageIdUserId(Long messageId, Long userId) {
        return entityManager.createQuery("SELECT count (mstar) > 0 FROM MessageStar mstar " +
                        "WHERE mstar.message.id= :messageId AND mstar.user.id = :userId", Boolean.class)
                .setParameter("messageId", messageId)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}

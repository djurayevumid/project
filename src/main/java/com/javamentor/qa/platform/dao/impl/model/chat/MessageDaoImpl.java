package com.javamentor.qa.platform.dao.impl.model.chat;

import com.javamentor.qa.platform.dao.abstracts.model.chat.MessageDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.chat.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MessageDaoImpl extends ReadWriteDaoImpl<Message, Long> implements MessageDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Message> getMessagesFromChat(Long chatId) {
        return entityManager.createQuery("FROM Message where chat.id = :chatId", Message.class)
                .setParameter("chatId", chatId)
                .getResultList();
    }
}

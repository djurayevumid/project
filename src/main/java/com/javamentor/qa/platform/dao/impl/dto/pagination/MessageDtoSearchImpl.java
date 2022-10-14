package com.javamentor.qa.platform.dao.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.message.MessageDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "messageSearch")
public class MessageDtoSearchImpl implements PageDtoDao<MessageDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MessageDto> getItems(Map<Object, Object> param) {
        int curPageNumber = (int) param.get("currentPageNumber");
        int itemsOnPage = (int) param.get("itemsOnPage");
        Long chatId = (Long) param.get("chatId");
        String stringSearch = (String) param.get("stringSearch");

        return entityManager.createQuery(
                "SELECT NEW com.javamentor.qa.platform.models.dto.message.MessageDto" +
                        "(m.id, " +
                        "m.message, " +
                        "us.nickname , " +
                        "us.id, " +
                        "us.imageLink, " +
                        "m.persistDate)" +
                        "FROM Message m JOIN Chat ch ON m.chat.id=ch.id LEFT JOIN " +
                        "User us ON m.userSender.id=us.id WHERE " +
                        "ch.id = :chatId AND " +
                        "m.message LIKE :stringSearch " +
                        "order by m.persistDate desc",
                MessageDto.class)
                .setParameter("chatId", chatId)
                .setParameter("stringSearch", "%" + stringSearch + "%")
                .setFirstResult((curPageNumber - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public long getTotalResultCount(Map<Object, Object> param) {

        Long chatId = (Long) param.get("chatId");
        String stringSearch = (String) param.get("stringSearch");

        return (Long) entityManager.createQuery(
                        "SELECT COUNT (m.id)" +
                                "FROM Message m JOIN Chat ch ON m.chat.id=ch.id LEFT JOIN " +
                                "User us ON m.userSender.id=us.id WHERE " +
                                "ch.id = :chatId AND " +
                                "m.message LIKE :stringSearch ")
                .setParameter("chatId", chatId)
                .setParameter("stringSearch", "%" + stringSearch + "%")
                .getSingleResult();
    }
}

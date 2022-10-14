package com.javamentor.qa.platform.dao.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.message.MessageDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
@Repository(value = "allSingleMessage")
public class MessageDtoAllSingleChat implements PageDtoDao<MessageDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<MessageDto> getItems(Map<Object, Object> param) {
        int curPageNumber = (int) param.get("currentPageNumber");
        int itemsOnPage = (int) param.get("itemsOnPage");
        Long singleChatId = (Long) param.get("singleId");
        List<MessageDto> o = entityManager.createQuery(
                "SELECT new com.javamentor.qa.platform.models.dto.message.MessageDto" +
                        "(m.id, " +
                        "m.message, " +
                        "us.nickname , " +
                        "us.id, " +
                        "us.imageLink, " +
                        "m.persistDate)" +
                        "from Message m JOIN Chat ch on m.chat.id=ch.id inner join " +
                        "User us on m.userSender.id=us.id where " +
                        "ch.id = :singleId " +
                        "order by m.persistDate desc",
                MessageDto.class)
                .setParameter("singleId", singleChatId).setFirstResult((curPageNumber - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .getResultList();
        return o;
    }

    @Override
    @SuppressWarnings("unchecked")
    public long getTotalResultCount(Map<Object, Object> param) {
        Long singleChatId = (Long) param.get("singleId");
        return (long) entityManager.createQuery("select count(m.id) from Message m JOIN Chat ch on " +
                "m.chat.id = ch.id " +
                "join User us on m.userSender.id = us.id where ch.id = :single")
                .setParameter("single",singleChatId)
                .getSingleResult();
    }
}

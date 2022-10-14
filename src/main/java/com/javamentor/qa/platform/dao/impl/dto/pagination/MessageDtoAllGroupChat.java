package com.javamentor.qa.platform.dao.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.message.MessageDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "AllGroupMessage")
public class MessageDtoAllGroupChat implements PageDtoDao<MessageDto> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MessageDto> getItems(Map<Object, Object> param) {
        int curPageNumber = (int) param.get("currentPageNumber");
        int itemsOnPage = (int) param.get("itemsOnPage");
        List<MessageDto> result = entityManager.createQuery(
                        "SELECT new com.javamentor.qa.platform.models.dto.message.MessageDto" +
                                "(m.id, " +
                                "m.message, " +
                                "us.nickname , " +
                                "us.id, " +
                                "us.imageLink, " +
                                "m.persistDate)" +
                                "from Message m JOIN Chat ch on m.chat.id=ch.id join GroupChat gc on gc.isGlobal=true and gc.id = ch.id inner join " +
                                "User us on m.userSender.id=us.id " +
                                "order by m.persistDate desc",
                        MessageDto.class)
                .setFirstResult((curPageNumber - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .getResultList();
        return result;
    }

    @Override
    public long getTotalResultCount(Map<Object, Object> param) {
        return (long) entityManager.createQuery("select count(m.id) from Message m JOIN Chat ch on " +
                        "m.chat.id = ch.id " +
                        "join User us on m.userSender.id = us.id where ch.chatType=1")
                .getSingleResult();
    }
}

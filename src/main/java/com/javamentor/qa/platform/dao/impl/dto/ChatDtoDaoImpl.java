package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.dto.chat.SingleChatDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ChatDtoDaoImpl implements ChatDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<GroupChatDto> getGroupChatById(long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                        "SELECT new com.javamentor.qa.platform.models.dto.chat.GroupChatDto" +
                                "(gc.id, c.title, c.persistDate )" +
                                "FROM Chat c " +
                                "JOIN GroupChat gc ON gc.chat.id = c.id " +
                                "WHERE gc.chat.id = : id"
                        , GroupChatDto.class)
                .setParameter("id", id));
    }

    @Override
    public List<ChatDto> getChatByChatName(String chatName) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = user.getId();
        return entityManager.createQuery(
                        "SELECT new com.javamentor.qa.platform.models.dto.chat.ChatDto" +
                                "(c.id, " +
                                "coalesce(c.title, us.fullName), " +
                                "us.imageLink," +
                                "m.message," +
                                "(EXISTS(SELECT ucp.id FROM UserChatPin ucp WHERE ucp.chat.id = c.id AND ucp.user.id = :currentUserId)), " +
                                "m.persistDate)" +
                                "FROM Chat c " +
                                "LEFT JOIN GroupChat gc on gc.chat.id = c.id and gc.isGlobal <> true " +
                                "LEFT JOIN SingleChat sc on c.id = sc.chat.id " +
                                "LEFT JOIN UserChatPin ucp on ucp.chat.id = c.id AND ucp.user.id = :currentUserId " +
                                "LEFT JOIN Message m ON c.id = m.chat.id  AND m.persistDate =" +
                                "(SELECT MAX(mgc.persistDate) FROM Message mgc " +
                                "WHERE c.id = mgc.chat.id)" +
                                "LEFT JOIN User us on us.id = " +
                                "CASE " +
                                "WHEN sc.userOne.id = :currentUserId THEN sc.userTwo.id " +
                                "WHEN sc.userTwo.id = :currentUserId THEN sc.userOne.id " +
                                "ELSE 0 END " +
                                "WHERE us.fullName LIKE :chatName " +
                                "OR c.title LIKE :chatName " +
                                "ORDER BY ucp.persistDate, m.persistDate DESC "
                        , ChatDto.class)
                .setParameter("chatName", "%" + chatName + "%")
                .setParameter("currentUserId", currentUserId)
                .getResultList();
    }


    @Override
    public List<SingleChatDto> getSingleChatByUserId(Long userId) {
        return entityManager.createQuery(
                        "SELECT new com.javamentor.qa.platform.models.dto.chat.SingleChatDto" +
                                "(sc.id," +
                                "us.nickname," +
                                "us.imageLink," +
                                "m.message," +
                                "m.persistDate)" +
                                "FROM SingleChat sc " +
                                "LEFT JOIN Message m ON sc.chat.id = m.chat.id " +
                                "AND m.id = (SELECT MAX(sm.id) FROM Message sm " +
                                "WHERE sc.chat.id = sm.chat.id and sm.persistDate = (SELECT MAX(msg.persistDate) FROM Message msg " +
                                "WHERE sc.chat.id = msg.chat.id)) " +
                                "INNER JOIN User us on us.id = sc.userOne.id  OR us.id = sc.userTwo.id " +
                                "WHERE us.id <> :id AND (sc.userOne.id = :id OR sc.userTwo.id = :id)"
                        , SingleChatDto.class)
                .setParameter("id", userId)
                .getResultList();
    }
}



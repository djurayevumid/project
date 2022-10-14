package com.javamentor.qa.platform.dao.impl.model.user;

import com.javamentor.qa.platform.dao.abstracts.model.user.UserChatPinDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.user.UserChatPin;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserChatPinDaoImpl extends ReadWriteDaoImpl<UserChatPin, Long> implements UserChatPinDao {

    @PersistenceContext
    private EntityManager entityManager;
}

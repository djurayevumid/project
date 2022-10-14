package com.javamentor.qa.platform.dao.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.question.VoteAnswerDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class VoteAnswerDaoImpl extends ReadWriteDaoImpl<VoteAnswer, Long> implements VoteAnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long getVoteCount(Long answerId) {
        return entityManager
                .createQuery("SELECT count(*) from VoteAnswer v where v.answer.id=:answerId", Long.class)
                .setParameter("answerId", answerId).getSingleResult();
    }

    @Override
    public Optional<VoteAnswer> findByAnswerAndUser(Long answerId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                        "FROM VoteAnswer v where v.answer.id=:answerId and v.user.id=:userId", VoteAnswer.class)
                .setParameter("answerId", answerId)
                .setParameter("userId", userId));
    }
}

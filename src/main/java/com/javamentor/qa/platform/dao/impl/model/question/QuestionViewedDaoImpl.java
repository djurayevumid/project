package com.javamentor.qa.platform.dao.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.question.QuestionViewedDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionViewedDaoImpl extends ReadWriteDaoImpl<QuestionViewed, Long> implements QuestionViewedDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Cacheable(value = "userView", key = "#questionId + #userEmail")
    public boolean checkThatQuestionViewByQuestionIdAndUserEmailIsEmpty(Long questionId, String userEmail) {
        return SingleResultUtil.getSingleResultOrNull(em.createQuery(
                        "SELECT qw " +
                                "FROM QuestionViewed qw " +
                                "WHERE qw.question.id = :qId AND qw.user.email = :uEmail", QuestionViewed.class)
                .setParameter("qId", questionId)
                .setParameter("uEmail", userEmail)).isEmpty();
    }

    @Override
    @CacheEvict(value = "userView", key ="#questionViewed.id + #questionViewed.user.email")
    public void persist(QuestionViewed questionViewed) {
        super.persist(questionViewed);
    }
}
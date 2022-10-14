package com.javamentor.qa.platform.dao.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.question.QuestionDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuestionDaoImpl extends ReadWriteDaoImpl<Question, Long> implements QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long countQuestions() {

        return (Long) (long) entityManager
                .createQuery("SELECT COUNT(q) FROM Question q WHERE q.isDeleted=: del")
                .setParameter("del", false)
                .getSingleResult();
    }
}

package com.javamentor.qa.platform.dao.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.question.AnswerDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

@Repository
public class AnswerDaoImpl extends ReadWriteDaoImpl<Answer, Long> implements AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteById(Long id) {
        entityManager.createQuery("update Answer set isDeleted = true where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void deleteByIdByModerator(Long id) {
        entityManager.createQuery("update Answer set isDeleted = true, isDeletedByModerator = true where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}



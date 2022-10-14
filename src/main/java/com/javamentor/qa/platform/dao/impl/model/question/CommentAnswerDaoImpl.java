package com.javamentor.qa.platform.dao.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.question.CommentAnswerDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentAnswerDaoImpl extends ReadWriteDaoImpl<CommentAnswer, Long> implements CommentAnswerDao {

    @PersistenceContext
    private EntityManager em;

}

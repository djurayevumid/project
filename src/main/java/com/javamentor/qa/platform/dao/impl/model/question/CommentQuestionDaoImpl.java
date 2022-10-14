package com.javamentor.qa.platform.dao.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.question.CommentQuestionDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentQuestionDaoImpl extends ReadWriteDaoImpl<CommentQuestion, Long> implements CommentQuestionDao {
    @PersistenceContext
    private EntityManager em;
}

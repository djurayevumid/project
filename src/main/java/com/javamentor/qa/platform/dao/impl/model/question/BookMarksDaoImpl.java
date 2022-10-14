package com.javamentor.qa.platform.dao.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.question.BookMarksDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.BookMarks;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BookMarksDaoImpl extends ReadWriteDaoImpl<BookMarks, Long> implements BookMarksDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean bookmarkIsExist(Long questionId, Long userId) {
        Long count = (Long) em.createQuery ("SELECT COUNT(b) FROM BookMarks b where b.question.id = :Qid and b.user.id =:Uid").
                setParameter("Qid", questionId).
                setParameter("Uid",userId).
                getSingleResult();
        return count >0;
    }
}
package com.javamentor.qa.platform.dao.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.question.QuestionViewDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("QuestionNoAnswer")
public class QuestionDtoNoAnswerDaoImpl implements PageDtoDao<QuestionViewDto> {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @SuppressWarnings("unchecked")
    public List<QuestionViewDto> getItems(Map<Object, Object> param) {

        int curPageNumber = (int) param.get("currentPageNumber");
        int itemsOnPage = (int) param.get("itemsOnPage");
        List<Long> trackedTags = ((List<Long>) param.get("trackedIds"));
        List<Long> ignoredTags = ((List<Long>) param.get("ignoredIds"));

        User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return entityManager.createQuery("SELECT NEW com.javamentor.qa.platform.models.dto.question.QuestionViewDto " +
                        "(q.id, " +
                        "q.title, " +
                        "q.user.id, " +
                        "q.user.fullName, " +
                        "q.user.imageLink, " +
                        "q.description, " +
                        "(SELECT COUNT(qv.id) FROM QuestionViewed qv WHERE qv.question.id = q.id) as count_views, " +
                        "(SELECT COUNT(a.id) FROM Answer a WHERE a.question.id = q.id AND a.isDeleted = false), " +
                        "((SELECT COUNT (up.vote) FROM VoteQuestion up WHERE up.vote = 'UP_VOTE' AND up.user.id = q.user.id) - " +
                        "(SELECT COUNT(down.vote) FROM VoteQuestion down WHERE down.vote = 'DOWN_VOTE' AND down.user.id = q.user.id)), " +
                        "(SELECT SUM (r.count) FROM Reputation r WHERE q.user.id = r.author.id), " +
                        "q.persistDateTime, " +
                        "q.lastUpdateDateTime, " +
                        "(SELECT v.vote FROM VoteQuestion v WHERE v.question.id = q.id AND v.user.id = :userId)," +
                        "EXISTS(SELECT b.id FROM BookMarks b WHERE b.question.id = q.id AND b.user.id = :userId)," +
                        "EXISTS(SELECT vans.id from VoteAnswer vans inner join User us on vans.user.id = us.id " +
                        "inner join Answer ans on vans.answer.id = ans.id inner join Question q on q.id = ans.question.id " +
                        "where us.id = :userId " +
                        "and ans.question.id = q.id " +
                        ")) " +
                        "FROM Question q  " +
                        "JOIN q.tags tgs " +
                        "WHERE q.id IN (SELECT q.id From Question q JOIN q.tags tgs WHERE :tracked IS NULL OR tgs.id IN :tracked) " +
                        "AND q.id NOT IN (SELECT q.id From Question q JOIN q.tags tgs WHERE tgs.id IN :ignored) " +
                        "AND q.id NOT IN (SELECT a.question.id FROM Answer a WHERE a.isDeleted = false) " +
                        "AND q.isDeleted = false " +
                        "GROUP BY q.id , q.user.fullName, q.user.imageLink ORDER BY q.id ", QuestionViewDto.class)
                .setParameter("tracked", trackedTags)
                .setParameter("ignored", ignoredTags)
                .setParameter("userId", userAuth.getId())
                .setFirstResult((curPageNumber - 1) * itemsOnPage).setMaxResults(itemsOnPage)
                .getResultList();
    }


    @Override
    public long getTotalResultCount(Map<Object, Object> param) {
        List<Long> ignoredTags = (List<Long>) param.get("ignoredIds");
        List<Long> trackedTags = (List<Long>) param.get("trackedIds");

        return (Long) entityManager.createQuery("SELECT COUNT(DISTINCT q.id) FROM Question q JOIN q.tags tgs" +
                        " WHERE q.id NOT IN (SELECT a.question.id FROM Answer a WHERE a.isDeleted = false) AND q.id IN (SELECT q.id From Question q JOIN q.tags tgs WHERE :tracked IS NULL OR tgs.id IN :tracked)" +
                        " AND q.id NOT IN (SELECT q.id From Question q JOIN q.tags tgs WHERE tgs.id IN :ignored) " +
                        "AND q.isDeleted = false")
                .setParameter("tracked", trackedTags)
                .setParameter("ignored", ignoredTags)
                .getSingleResult();
    }
}

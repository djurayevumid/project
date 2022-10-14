package com.javamentor.qa.platform.dao.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import com.javamentor.qa.platform.models.dto.question.QuestionViewDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "QuestionDtoPaginationByTag")
public class QuestionDtoTagDaoImpl implements PageDtoDao<QuestionViewDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<QuestionViewDto> getItems(Map<Object, Object> param) {
        int curPageNumber = (int) param.get("currentPageNumber");
        int itemsOnPage = (int) param.get("itemsOnPage");
        Long tagId = (Long) param.get("tagId");
        int filterByTime = (int) param.get("filterByTime");

        User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return entityManager.createQuery(
                        "SELECT new com.javamentor.qa.platform.models.dto.question.QuestionViewDto" +
                                "(q.id, " +
                                "q.title, " +
                                "q.user.id, " +
                                "q.user.fullName, " +
                                "q.user.imageLink, " +
                                "q.description, " +
                                "(SELECT COUNT(qv.id) FROM QuestionViewed qv WHERE qv.question.id = q.id) as count_views, " +
                                "(SELECT COUNT(answer.id) FROM Answer answer WHERE answer.user.id = q.user.id), " +
                                "(SELECT COUNT (up.vote) FROM VoteQuestion up WHERE up.vote = 'UP_VOTE' AND up.user.id = q.user.id) - " +
                                "(SELECT COUNT(down.vote) FROM VoteQuestion down WHERE down.vote = 'DOWN_VOTE' AND down.user.id = q.user.id)," +
                                "(SELECT SUM (r.count) FROM Reputation r WHERE q.user.id = r.author.id), " +
                                "q.persistDateTime, " +
                                "q.lastUpdateDateTime, " +
                                "(SELECT v.vote FROM VoteQuestion v WHERE v.question.id = q.id AND v.user.id = :userId)," +
                                "EXISTS(SELECT b.id FROM BookMarks b WHERE b.question.id = q.id AND b.user.id = :userId), " +
                                "EXISTS(SELECT vans.id from VoteAnswer vans inner join User us on vans.user.id = us.id " +
                                "inner join Answer ans on vans.answer.id = ans.id inner join Question q on q.id = ans.question.id " +
                                "where us.id = :userId " +
                                "and ans.question.id = q.id " +
                                ")) " +
                                "FROM Question q " +
                                "JOIN q.tags tgs " +
                                "WHERE q.id IN (SELECT q.id From Question q JOIN q.tags tgs WHERE :tagId IS NULL OR tgs.id IN :tagId)" +
                                "AND q.isDeleted = false " +
                                "AND q.persistDateTime > (current_date - :filterByTime ) " +
                                "GROUP BY q.id, q.user.fullName, q.user.imageLink ORDER BY q.persistDateTime DESC ",
                        QuestionViewDto.class)
                .setParameter("tagId", tagId)
                .setParameter("userId", userAuth.getId())
                .setParameter("filterByTime", filterByTime)
                .setFirstResult((curPageNumber - 1) * itemsOnPage).setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public long getTotalResultCount(Map<Object, Object> param) {
        Long tagId = (Long) param.get("tagId");
        return (Long) entityManager.createQuery("SELECT COUNT(q) FROM Question q JOIN q.tags t WHERE t.id = :tagId " +
                        "AND q.isDeleted = false")
                .setParameter("tagId", tagId)
                .getSingleResult();
    }
}

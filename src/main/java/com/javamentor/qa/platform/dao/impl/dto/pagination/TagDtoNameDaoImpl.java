package com.javamentor.qa.platform.dao.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import org.hibernate.criterion.MatchMode;
import com.javamentor.qa.platform.models.dto.tag.TagViewDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Repository(value = "TagPaginationByName")
public class TagDtoNameDaoImpl implements PageDtoDao<TagViewDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TagViewDto> getItems(Map<Object, Object> param) {

        int curPageNumber = (int) param.get("currentPageNumber");
        int itemsOnPage = (int) param.get("itemsOnPage");
        LocalDateTime localDateTime = LocalDateTime.now();
        List<TagViewDto> items = entityManager.createQuery("SELECT new com.javamentor.qa.platform.models.dto.tag.TagViewDto" +
                                            "(t.id, t.name, t.description, count(DISTINCT qa.id) , count(DISTINCT qd.id), count(DISTINCT qw.id)) " +
                                            "FROM Tag t " +
                                            "LEFT JOIN t.questions qa ON qa.isDeleted = false " +
                                            "LEFT JOIN t.questions qd ON qd.persistDateTime <= :now " +
                                            "and :oneDay < qd.persistDateTime and qd.isDeleted = false " +
                                            "LEFT JOIN t.questions qw ON qw.persistDateTime <= :now " +
                                            "and :oneWeek < qw.persistDateTime and qw.isDeleted = false " +
                                            "where lower(t.name) LIKE :filter " +
                                            "GROUP BY t " +
                                            "ORDER BY t.name", TagViewDto.class)
                .setParameter("oneDay", localDateTime.minusDays(1))
                .setParameter("now", localDateTime)
                .setParameter("oneWeek", localDateTime.minusDays(7))
                .setParameter("filter", MatchMode.ANYWHERE.toMatchString((String) param.get("filter")).toLowerCase(Locale.ROOT))
                .setFirstResult((curPageNumber - 1) * itemsOnPage).setMaxResults(itemsOnPage)
                .getResultList();
        return items;
    }

    @Override
    public long getTotalResultCount(Map<Object, Object> param) {
        return (Long) entityManager.createQuery("SELECT COUNT(id) FROM Tag where lower(name) LIKE :filter")
                .setParameter("filter", MatchMode.ANYWHERE.toMatchString((String) param.get("filter")).toLowerCase(Locale.ROOT))
                .getSingleResult();
    }
}

package com.javamentor.qa.platform.dao.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Repository(value = "paginationByPersistDate")
public class UserDtoPersistDateDaoImpl implements PageDtoDao<UserDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserDto> getItems(Map<Object, Object> param) {
        int curPageNumber = (int) param.get("currentPageNumber");
        int itemsOnPage = (int) param.get("itemsOnPage");
        return entityManager.createQuery("SELECT new com.javamentor.qa.platform.models.dto.user.UserDto" +
                        "(u.id, u.nickname, u.city, SUM(COALESCE(r.count, 0)), u.imageLink) " +
                        "FROM User u LEFT JOIN Reputation r ON u.id = r.author.id " +
                        "where lower(u.nickname) LIKE :filter " +
                                "GROUP BY u.id " +
                        "ORDER BY u.persistDateTime", UserDto.class)
                .setParameter("filter", MatchMode.ANYWHERE.toMatchString((String) param.get("filter")).toLowerCase(Locale.ROOT))
                .setFirstResult((curPageNumber - 1) * itemsOnPage).setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public long getTotalResultCount(Map<Object, Object> param) {
        return (Long) entityManager.createQuery("SELECT COUNT (id) FROM User where lower(nickname) LIKE :filter")
                .setParameter("filter", MatchMode.ANYWHERE.toMatchString((String) param.get("filter")).toLowerCase(Locale.ROOT))
                .getSingleResult();
    }
}

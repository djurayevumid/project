package com.javamentor.qa.platform.dao.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import org.hibernate.criterion.MatchMode;
import org.hibernate.query.Query;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Repository(value = "UserPaginationByVote")
public class UserDtoVoteDaoImpl implements PageDtoDao<UserDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDto> getItems(Map<Object, Object> param) {
        int curPageNumber = (int) param.get("currentPageNumber");
        int itemsOnPage = (int) param.get("itemsOnPage");

        return entityManager.createQuery("SELECT " +
                        "u.id, u.nickname, u.city, SUM(COALESCE(r.count, 0)), u.imageLink, " +
                        "((SELECT COUNT(vq.vote) FROM VoteQuestion vq WHERE vq.user.id = u.id) +" +
                        "(SELECT COUNT(va.vote) FROM VoteAnswer va WHERE va.user.id = u.id)) AS sort " +
                        " FROM User u LEFT JOIN Reputation r ON u.id = r.author.id " +
                        " where lower(u.nickname) LIKE :filter " +
                        " GROUP BY u.id" +
                        " ORDER BY sort DESC", Tuple.class).unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] tuples, String[] aliases) {
                        UserDto userDto = new UserDto((long) tuples[0], (String) tuples[1], (String) tuples[2], (long) tuples[3],
                                (String) tuples[4]);
                        return userDto;
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                }).setParameter("filter", MatchMode.ANYWHERE.toMatchString((String) param.get("filter")).toLowerCase(Locale.ROOT))
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

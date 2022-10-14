package com.javamentor.qa.platform.dao.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "AllUsers")
public class UserDtoAllUsersDaoImpl implements PageDtoDao<UserDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserDto> getItems(Map<Object, Object> param) {
        return entityManager.createQuery("SELECT new com.javamentor.qa.platform.models.dto.UserDto" +
                        "(u.id, u.nickname, u.city, SUM(COALESCE(r.count, 0)), u.imageLink) " +
                        "FROM User u LEFT JOIN Reputation r ON u.id = r.author.id GROUP BY u.id", UserDto.class)
                .getResultList();
    }

    @Override
    public long getTotalResultCount(Map<Object, Object> param) {
        return (Long) entityManager.createQuery("SELECT COUNT (id) FROM User").getSingleResult();
    }
}

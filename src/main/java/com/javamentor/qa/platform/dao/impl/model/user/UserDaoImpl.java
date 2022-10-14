package com.javamentor.qa.platform.dao.impl.model.user;

import com.javamentor.qa.platform.dao.abstracts.model.user.UserDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl extends ReadWriteDaoImpl<User, Long> implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    @Cacheable(value = "user-email", key = "#email")
    public Optional<User> findByEmail(String email) {
        String hql = "FROM User u JOIN FETCH u.role WHERE u.email = :email";
        return SingleResultUtil.getSingleResultOrNull(
                (TypedQuery<User>) entityManager.createQuery(hql).setParameter("email", email)
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    @CacheEvict(value = {"user-exist-email","user-email"} , key = "#email")
    public void changePassword(String email, String password) {
        String hql = "update User set password = :passwordParam where email = :email";
        entityManager.createQuery(hql)
                .setParameter("passwordParam", password)
                .setParameter("email", email)
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    @Cacheable(value = "user-exist-email", key = "#email")
    public boolean existByEmailEnabledUser(String email) {
        String hql = "FROM User u WHERE u.email = :email AND NOT u.isEnabled";
        TypedQuery<User> query = (TypedQuery<User>) entityManager.createQuery(hql).setParameter("email", email);
        Optional<User> userOptional= SingleResultUtil.getSingleResultOrNull(query);
        return userOptional.isPresent();

    }

    public void changeIsEnable(String email){
        String hql = "update User set isEnabled = :isEnabled where email = :email";
        entityManager.createQuery(hql)
                .setParameter("isEnabled", false)
                .setParameter("email", email)
                .executeUpdate();
    }

    @Override
    @CacheEvict(value = {"user-exist-email","user-email"} , key = "#e.email")
    public void update(User e){
        super.update(e);
    }

    @Override
    @CacheEvict(value = {"user-exist-email","user-email"} , key = "#e.email")
    public void delete(User e){
        super.delete(e);
    }

    @Override
    @CacheEvict(value = {"user-exist-email","user-email"} , allEntries = true)
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    @CacheEvict(value = {"user-exist-email","user-email"} , allEntries = true)
    public void deleteAll(Collection<User> entities) {
        super.deleteAll(entities);
    }

    @Override
    @CacheEvict(value = {"user-exist-email","user-email"} , allEntries = true)
    public void updateAll(Iterable<? extends User> entities) {
        super.updateAll(entities);
    }

    @Override
    public List<User> getAllUsers(List<Long> ids){
        return entityManager
                .createQuery("from User user WHERE user.id IN :ids")
                .setParameter("ids", ids)
                .getResultList();
    }
}

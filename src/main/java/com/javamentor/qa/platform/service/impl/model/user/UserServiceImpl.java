package com.javamentor.qa.platform.service.impl.model.user;

import com.javamentor.qa.platform.dao.abstracts.model.user.UserDao;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.user.UserService;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl extends ReadWriteServiceImpl<User, Long> implements UserService {

    private final UserDao userDao;
    @Autowired
    private CacheManager cacheManager;

    public UserServiceImpl(UserDao userDao) {
        super(userDao);
        this.userDao = userDao;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Transactional
    public void changeIsEnable(String email){
        userDao.changeIsEnable(email);
        evictUserCache(email);
    }

    @Override
    @Transactional
    public void changePasswordByEmail(String email, String password) {
        String passHash = BCrypt.hashpw(password, BCrypt.gensalt());
        userDao.changePassword(email, passHash);
        evictUserCache(email);
    }

    public void evictUserCache(String email){
        cacheManager.getCache("user-email").evict(email);
    }
}

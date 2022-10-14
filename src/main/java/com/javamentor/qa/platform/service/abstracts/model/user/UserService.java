package com.javamentor.qa.platform.service.abstracts.model.user;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;
import java.util.Optional;

public interface UserService extends ReadWriteService<User, Long> {

    Optional<User> findByEmail(String email);
    void changePasswordByEmail(String email, String password);

    void changeIsEnable(String email);
}

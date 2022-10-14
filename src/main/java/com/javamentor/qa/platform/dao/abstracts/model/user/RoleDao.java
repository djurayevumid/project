package com.javamentor.qa.platform.dao.abstracts.model.user;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.user.Role;

import java.util.Optional;

public interface RoleDao extends ReadWriteDao<Role, Long> {
    Optional<Role> findByName(String name);
}

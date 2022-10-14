package com.javamentor.qa.platform.service.abstracts.model.user;

import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

import java.util.Optional;

public interface RoleService extends ReadWriteService<Role, Long> {
    Optional<Role> findRoleByName(String name);
}

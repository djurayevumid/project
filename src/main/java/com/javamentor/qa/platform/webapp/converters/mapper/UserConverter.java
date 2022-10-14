package com.javamentor.qa.platform.webapp.converters.mapper;

import com.javamentor.qa.platform.models.dto.user.UserAuthDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserConverter {
    public abstract UserAuthDto UserToUserAuthDto (User user);
}

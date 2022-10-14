package com.javamentor.qa.platform.webapp.converters.mapper;

import com.javamentor.qa.platform.models.dto.chat.CreateSingleChatDto;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(componentModel = "spring", imports = {
        SecurityContextHolder.class,
})
public abstract class CreateSingleChatMapper {
@Mapping(target = "userOne", expression = "java((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())")
@Mapping(target = "userTwo", source = "userTwo1")
    public abstract SingleChat toModel(CreateSingleChatDto createSingleChatDto, User userTwo1);
}
package com.javamentor.qa.platform.webapp.converters.mapper;

import com.javamentor.qa.platform.dao.abstracts.model.user.UserDao;
import com.javamentor.qa.platform.exception.UsersNotFoundException;
import com.javamentor.qa.platform.models.dto.chat.CreateGroupChatDto;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class GroupChatConverter {
    @Autowired
    protected UserDao userDao;
    @Mapping(source = "createGroupChatDto.chatName", target = "chat", qualifiedByName = "fillingChatOfGroupChat")
    @Mapping(source = "createGroupChatDto.userIds", target = "users", qualifiedByName = "fillingUserListOfGroupChat")
    public abstract GroupChat createGroupChatToGroupChat(CreateGroupChatDto createGroupChatDto);

    @Named("fillingUserListOfGroupChat")
    public Set<User> fillingUserListOfGroupChat(List<Long> userIds) throws UsersNotFoundException {
        Set<User> users = new HashSet<>(userDao.getAllUsers(userIds));
        if (users.size() != userIds.size()) throw new UsersNotFoundException("Users was not found!");
        return users;
    }

    @Named("fillingChatOfGroupChat")
    public Chat fillingChatOfGroupChat(String chatName) {
        Chat chat = new Chat(ChatType.GROUP);
        chat.setTitle(chatName);
        return chat;
    }
}

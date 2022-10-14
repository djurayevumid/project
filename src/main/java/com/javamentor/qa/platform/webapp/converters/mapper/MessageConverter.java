package com.javamentor.qa.platform.webapp.converters.mapper;

import com.javamentor.qa.platform.models.dto.chat.MessageRequestDto;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.chat.GroupChatService;
import com.javamentor.qa.platform.service.abstracts.model.user.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@Mapper(componentModel = "spring")
public abstract class MessageConverter {

    @Autowired
    protected GroupChatService groupChatService;
    @Autowired
    protected UserService userService;

    @Mapping(source = "messageRequestDto.chatId", target = "chat", qualifiedByName = "chatIdToChat")
    @Mapping(source = "messageRequestDto.senderId", target = "userSender", qualifiedByName = "senderIdToUser")
    public abstract Message MessageRequestDtoFromGroupChatToMessage(MessageRequestDto messageRequestDto);

    @Named("chatIdToChat")
    public Chat chatIdToChat(Long chatId) {
        Optional<GroupChat> chat = groupChatService.getById(chatId);
        if (chat.isPresent()) {
            return chat.get().getChat();
        }
        throw new RuntimeException("Некорректный chatId");
    }

    @Named("senderIdToUser")
    public User senderIdToUser(Long senderId) {
        Optional<User> user = userService.getById(senderId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new RuntimeException("Некорректный senderId");
    }
}

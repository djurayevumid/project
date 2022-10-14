package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.dto.chat.SingleChatDto;

import java.util.List;
import java.util.Optional;

public interface ChatDtoDao {
    Optional<GroupChatDto> getGroupChatById(long id);

    List<ChatDto> getChatByChatName(String chatName);

    List<SingleChatDto> getSingleChatByUserId(Long userId);


}

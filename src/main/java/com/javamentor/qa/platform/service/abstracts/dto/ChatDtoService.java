package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.dto.chat.SingleChatDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface ChatDtoService {

    List<ChatDto> getChatByChatName(String chatName);
    List<SingleChatDto> getSingleChatByUserId(Long userId);
    Optional<GroupChatDto> getGroupChatByPaginationMessages(int currentPageNumber,
                                                            int itemsOnPage, Map<Object, Object> map);

}

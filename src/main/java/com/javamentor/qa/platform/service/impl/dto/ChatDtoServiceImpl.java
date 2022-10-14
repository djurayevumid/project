package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.dto.chat.SingleChatDto;
import com.javamentor.qa.platform.models.dto.message.MessageDto;
import com.javamentor.qa.platform.models.dto.page.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.MessageDtoService;
import com.javamentor.qa.platform.webapp.converters.mapper.CreateSingleChatMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ChatDtoServiceImpl implements ChatDtoService {
    private final ChatDtoDao chatDtoDao;
    private final MessageDtoService messageDtoService;
    private final CreateSingleChatMapper createSingleChatMapper;

    public ChatDtoServiceImpl(ChatDtoDao chatDtoDao, MessageDtoService messageDtoService, CreateSingleChatMapper createSingleChatMapper) {
        this.chatDtoDao = chatDtoDao;
        this.messageDtoService = messageDtoService;
        this.createSingleChatMapper = createSingleChatMapper;
    }


    @Transactional
    @Override
    public Optional<GroupChatDto> getGroupChatByPaginationMessages(int currentPageNumber,
                                                                   int itemsOnPage, Map<Object, Object> map) {
        Long groupChatID = (Long) map.get("groupId");
        Optional<GroupChatDto> groupChatDto = Optional.of(chatDtoDao.getGroupChatById(groupChatID)
                .orElseThrow());
        PageDto<MessageDto> pageDto = messageDtoService.getPage(currentPageNumber, itemsOnPage, map);
        groupChatDto.ifPresent(dto -> dto.setMessageDtoPageDto(pageDto));

        return groupChatDto;
    }

    @Override
    public List<ChatDto> getChatByChatName(String chatName) {
        return chatDtoDao.getChatByChatName(chatName);
    }

    @Override
    public List<SingleChatDto> getSingleChatByUserId(Long userId) {
        return chatDtoDao.getSingleChatByUserId(userId);
    }


}

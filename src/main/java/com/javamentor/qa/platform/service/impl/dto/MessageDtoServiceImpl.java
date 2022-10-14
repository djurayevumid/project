package com.javamentor.qa.platform.service.impl.dto;



import com.javamentor.qa.platform.models.dto.message.MessageDto;

import com.javamentor.qa.platform.models.dto.page.PageDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.MessageDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class MessageDtoServiceImpl extends PageDtoServiceImpl<MessageDto> implements MessageDtoService {

}

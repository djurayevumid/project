package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.chat.MessageRequestDto;
import com.javamentor.qa.platform.models.dto.question.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.chat.MessageService;
import com.javamentor.qa.platform.webapp.converters.mapper.MessageConverter;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MessageSend {
    private final MessageConverter messageConverter;
    private final MessageService messageService;

    @MessageMapping("/groupchat")
    @SendTo("/topic/public")
    public MessageRequestDto MessageRequestDto(@Payload MessageRequestDto messageRequestDto) {
        messageService.persist(messageConverter.MessageRequestDtoFromGroupChatToMessage(messageRequestDto));
        return messageRequestDto;
    }
}

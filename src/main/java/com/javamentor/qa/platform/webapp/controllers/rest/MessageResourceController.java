package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.dto.message.MessageDto;
import com.javamentor.qa.platform.models.dto.page.PageDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.MessageDtoService;
import com.javamentor.qa.platform.service.abstracts.model.chat.MessageStarService;
import com.javamentor.qa.platform.webapp.configs.SwaggerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {SwaggerConfig.MESSAGE_RESOURCE_CONTROLLER})
@RestController
@RequestMapping("api/user/message")
@AllArgsConstructor
public class MessageResourceController {

    private final MessageStarService messageStarService;
    private final MessageDtoService messageDtoService;


    @Operation(summary = "Delete an message favorite by id", responses = {
            @ApiResponse(description = "Message favorite was deleted from DB", responseCode = "200"),
            @ApiResponse(description = "Received if no message favorite with such id exists in DB", responseCode = "400")
    })
    @DeleteMapping("/star")
    public ResponseEntity<?> deleteMessageStar(@RequestBody Long messageId) {
        User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (messageStarService.existsMessageStarByMessageIdUserId(messageId, userAuth.getId())) {
            messageStarService.deleteMessageStarByMessageIdUserId(messageId, userAuth.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("The message is not a favorite", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Get all messages from global chat", responses = {
            @ApiResponse(description = "Message favorite was deleted from DB", responseCode = "200"),
    })
    @GetMapping("/global")
    public ResponseEntity<?> getGlobalMessages(
            @ApiParam(value = "positive number representing number of current page", required = true)
            @RequestParam @Positive(message = "current page must be positive number") int currPage,
            @ApiParam(value = "positive number representing number of items to show on page")
            @RequestParam(required = false, defaultValue = "10") @Positive(message = "items must be positive number") int items) {
        Map<Object, Object> param = new HashMap<>();
        param.put("class", "AllGroupMessage");
        PageDto<MessageDto> globalChatMessages = messageDtoService.getPage(currPage, items, param);
        return new ResponseEntity<>(globalChatMessages, HttpStatus.OK);
    }
}

package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.CreateGroupChatDto;
import com.javamentor.qa.platform.models.dto.chat.CreateSingleChatDto;
import com.javamentor.qa.platform.models.dto.chat.SingleChatDto;
import com.javamentor.qa.platform.models.dto.message.MessageDto;
import com.javamentor.qa.platform.models.dto.page.PageDto;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.MessageDtoService;
import com.javamentor.qa.platform.service.abstracts.model.chat.ChatService;
import com.javamentor.qa.platform.service.abstracts.model.chat.GroupChatService;
import com.javamentor.qa.platform.service.abstracts.model.chat.SingleChatService;
import com.javamentor.qa.platform.service.abstracts.model.user.UserService;
import com.javamentor.qa.platform.webapp.configs.SwaggerConfig;
import com.javamentor.qa.platform.webapp.converters.mapper.CreateSingleChatMapper;
import com.javamentor.qa.platform.webapp.converters.mapper.GroupChatConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.javamentor.qa.platform.models.entity.chat.ChatType.GROUP;
import static com.javamentor.qa.platform.models.entity.chat.ChatType.SINGLE;


@Validated
@RestController
@RequestMapping("/api/user/chat")
@Api(tags = {SwaggerConfig.CHAT_RESOURCE_CONTROLLER})
@AllArgsConstructor
public class ChatResourceController {

    private final ChatDtoService chatDtoService;
    private final MessageDtoService pageDtoService;
    private final UserService userService;
    private final SingleChatService singleChatService;
    private final CreateSingleChatMapper createSingleChatMapper;
    private final GroupChatService groupChatService;
    private final ChatService chatService;
    private final GroupChatConverter groupChatConverter;


    @GetMapping(path = "{id}/group/message")
    @Operation(summary = "Get page of group_chat dto with pagination messages select by id", responses = {
            @ApiResponse(description = " success", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MessageDto.class)))),
            @ApiResponse(description = "there isn`t curPage parameter in url or parameters in url are not positives numbers", responseCode = "400")
    })
    public ResponseEntity<?> getGroupChatWithPaginationMessages(@PathVariable Long id,
                                                                @ApiParam(value = "positive number representing number of current page", required = true)
                                                                @RequestParam @Positive(message = "current page must be positive number") int currPage,
                                                                @ApiParam(value = "positive number representing number of items to show on page")
                                                                @RequestParam(required = false, defaultValue = "30")
                                                                @Positive(message = "items must be positive number")
                                                                @Min(value = 1, message = "Items not transferred") int items) {
        User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<Object, Object> map = new HashMap<>();
        map.put("class", "allGroupMessages");
        map.put("groupId", id);
        map.put("userAuth", userAuth);

        return ResponseEntity.ok(chatDtoService.getGroupChatByPaginationMessages(currPage, items, map));
    }

    @GetMapping("/single")
    @Operation(summary = "Get current user SingleChats", responses = {
            @ApiResponse(description = "Get current user SingleChats success", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SingleChatDto.class)))),
            @ApiResponse(description = "No SingleChats with such user - return empty list", responseCode = "200"),
            @ApiResponse(responseCode = "403", description = "user failed authorization")
    })
    public ResponseEntity<?> getSingleChatDtoByAuthUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(chatDtoService.getSingleChatByUserId(user.getId()));
    }


    @Operation(summary = "Add SingleChat", responses = {
            @ApiResponse(description = "Add SingleChat", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CreateSingleChatDto.class)))),
            @ApiResponse(responseCode = "403", description = "user failed authorization")
    })
    @PostMapping("/single")
    public ResponseEntity<?> addSingleChat(@RequestBody @Valid CreateSingleChatDto createSingleChatDto) {
        Optional<User> userTwo = userService.getById(createSingleChatDto.getUserId());
        if (userTwo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User has not exist!");
        }
        SingleChat singleChat = createSingleChatMapper.toModel(createSingleChatDto, userTwo.get());
        singleChatService.addSingleChatAndMessage(singleChat, createSingleChatDto.getMessage());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(path = "{id}/single/message")
    @Operation(summary = "Get page of questions with pagination selected by chat id", responses = {
            @ApiResponse(description = " success", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MessageDto.class)))),
            @ApiResponse(description = "there isn`t curPage parameter in url or parameters in url are not positives numbers", responseCode = "400")
    })
    public ResponseEntity<?> getPageAllSingleMessages(
            @PathVariable Long id,
            @ApiParam(value = "positive number representing number of current page", required = true)
            @RequestParam @Positive(message = "current page must be positive number") int currPage,
            @ApiParam(value = "positive number representing number of items to show on page")
            @RequestParam(required = false, defaultValue = "10") @Positive(message = "items must be positive number") int items) {
        User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<Object, Object> map = new HashMap<>();
        map.put("class", "allSingleMessage");
        map.put("singleId", id);
        map.put("userAuth", userAuth);
        PageDto<MessageDto> page = pageDtoService.getPage(currPage, items, map);
        return ResponseEntity.ok(page);

    }

    @Operation(summary = "Mark as deleted an chat by id for current user", responses = {
            @ApiResponse(description = "Chat was mark as deleted", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = SingleChat.class))),
            @ApiResponse(description = "Received if no chat with such id exists in DB" +
                    " or the current user does not have access to it", responseCode = "400"),
            @ApiResponse(description = "Received if the current user already deleted the chat with this id", responseCode = "406")
    })
    @DeleteMapping("/{chatId}")
    public ResponseEntity<?> markChatAsDelForUser(@PathVariable Long chatId) {
        Optional<Chat> chat = chatService.getById(chatId);
        if (chat.isPresent()) {
            if (chat.get().getChatType().equals(SINGLE)) {
                singleChatService.markSingleChatAsDelForUser(chatId);
            }
            if (chat.get().getChatType().equals(GROUP)) {
                groupChatService.delUserFromGroupChat(chatId);
            }
            return new ResponseEntity<>("Chat was deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("There is no chat with this id in the database or no access", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @Operation(summary = "Get current user chats by chatName", responses = {
            @ApiResponse(description = "Get current user chats success", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChatDto.class)))),
            @ApiResponse(description = "No chats with such user - return empty list", responseCode = "200"),
            @ApiResponse(responseCode = "403", description = "user failed authorization")
    })
    public ResponseEntity<?> getChatByName(@RequestParam String chatName) {
        List<ChatDto> chatsDto = chatDtoService.getChatByChatName(chatName);
        return ResponseEntity.ok(chatsDto);

    }

    @PostMapping("/group")
    @Operation(summary = "Description of method setGroupChatDto", responses = {
            @ApiResponse(description = "Description createGroupChatDto", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CreateGroupChatDto.class)))),
            @ApiResponse(description = "No access", responseCode = "403")
    })
    public ResponseEntity<?> setGroupChatDto(@RequestBody @Valid CreateGroupChatDto createGroupChatDto) {
        GroupChat groupChat = groupChatConverter.createGroupChatToGroupChat(createGroupChatDto);
        groupChatService.persist(groupChat);
        return new ResponseEntity("Method setGroupChatDto is working correctly", HttpStatus.OK);
    }

    @GetMapping(path = "{id}/group/message/find")
    @Operation(summary = "Get page of questions with pagination selected by chat id", responses = {
            @ApiResponse(description = " success", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MessageDto.class)))),
            @ApiResponse(description = "there isn`t curPage parameter in url or parameters in url are not positives numbers", responseCode = "400")
    })
    public ResponseEntity<?> getPageMessageSearch(
            @PathVariable Long id,
            @ApiParam(value = "positive number representing number of current page", required = true)
            @RequestParam(required = false, defaultValue = "20") @Positive(message = "items must be positive number") int items,
            @ApiParam(value = "positive number representing number of current page", required = true)
            @RequestParam @Positive(message = "current page must be positive number") int currPage,
            @RequestParam String word) {
        Map<Object, Object> map = new HashMap<>();
        map.put("class", "messageSearch");
        map.put("chatId", id);
        map.put("stringSearch", word);
        PageDto<MessageDto> page = pageDtoService.getPage(currPage, items, map);
        return ResponseEntity.ok(page);
    }

}


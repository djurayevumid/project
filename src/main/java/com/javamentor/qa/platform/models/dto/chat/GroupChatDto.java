package com.javamentor.qa.platform.models.dto.chat;


import com.javamentor.qa.platform.models.dto.message.MessageDto;

import com.javamentor.qa.platform.models.dto.page.PageDto;
import lombok.*;



import java.time.LocalDateTime;



@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupChatDto {

    private Long id;
    private String chatName;
    private LocalDateTime persistDateTime;
    private PageDto<MessageDto> messageDtoPageDto;

    public GroupChatDto(Long id, String chatName, LocalDateTime persistDateTime) {
        this.id = id;
        this.chatName = chatName;
        this.persistDateTime = persistDateTime;
    }


}


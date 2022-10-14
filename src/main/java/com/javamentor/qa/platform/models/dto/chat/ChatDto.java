package com.javamentor.qa.platform.models.dto.chat;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {

    private Long id;
    private String name;
    private String image;
    private String lastMessage;
    private boolean isChatPin;
    private LocalDateTime persistDateTimeLastMessage;


}

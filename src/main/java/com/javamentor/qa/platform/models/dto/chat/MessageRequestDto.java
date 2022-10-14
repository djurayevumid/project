package com.javamentor.qa.platform.models.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;
import lombok.Builder;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequestDto {
    private String message;
    private Long senderId;
    private String senderNickname;
    private String senderImage;
    private Long chatId;
}

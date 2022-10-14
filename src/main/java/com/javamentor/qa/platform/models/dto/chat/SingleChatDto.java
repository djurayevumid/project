package com.javamentor.qa.platform.models.dto.chat;

import java.time.LocalDateTime;

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
public class SingleChatDto {
    private Long id;
    private String name;
    private String image;
    private String lastMessage;
    private LocalDateTime persistDateTimeLastMessage;
}
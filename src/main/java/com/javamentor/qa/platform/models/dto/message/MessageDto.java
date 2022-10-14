package com.javamentor.qa.platform.models.dto.message;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
public class MessageDto {
    private Long id;
    private String message;
    private String nickName;
    private Long userId;
    private String image;
    private LocalDateTime persistDateTime;
    public MessageDto(Long id, String message, String nickName, Long userId, String image, LocalDateTime persistDateTime) {
        this.id = id;
        this.message = message;
        this.nickName = nickName;
        this.userId = userId;
        this.image = image;
        this.persistDateTime = persistDateTime;
    }
}

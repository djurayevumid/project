package com.javamentor.qa.platform.models.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dto of CreateSingeChat")
public class CreateSingleChatDto {

    @Schema(description = "Message recipient ID", example = "15")
    private long userId;

    @NotBlank(message = "Message cannot be empty")
    @Schema(description = "Text of message", example = "Hello!")
    private String message;
}

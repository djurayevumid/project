package com.javamentor.qa.platform.models.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Description CreateGroupChatDto")
public class CreateGroupChatDto {

    @NotBlank(message = "chatName is null!")
    @Schema(description = "Name chatName", example = "Title")
    private String chatName;

    @NotEmpty(message = "userIds is empty!")
    @NotNull(message = "userIds is null!")
    @Schema(description = "Number id", example = "100")
    private List<Long> userIds;
}

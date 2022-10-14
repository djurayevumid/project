package com.javamentor.qa.platform.models.dto.question;

import com.javamentor.qa.platform.models.dto.tag.TagDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
@NoArgsConstructor
public class QuestionCreateDto {
    @NotBlank(message = "Поле title не может быть пустым или null")
    private String title;
    @NotBlank(message = "Поле description не может быть пустым или null")
    private String description;
    @NotEmpty(message = "Поле tags не может быть пустым или null")
    private List<TagDto> tags;
}

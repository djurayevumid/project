package com.javamentor.qa.platform.models.dto.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCommentDto {

    private Long id;
    private Long questionId;
    private LocalDateTime lastRedactionDate;
    private LocalDateTime persistDate;
    private String text;
    private Long userId;
    private String imageLink;
    private Long reputation;
}

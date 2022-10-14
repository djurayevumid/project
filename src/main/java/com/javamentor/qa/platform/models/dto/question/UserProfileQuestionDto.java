package com.javamentor.qa.platform.models.dto.question;

import com.javamentor.qa.platform.models.dto.tag.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
public class UserProfileQuestionDto {
    private Long questionId;
    private String title;
    private List<TagDto> tags;
    private Long countAnswer;
    private LocalDateTime persistDate;

    public UserProfileQuestionDto(Long questionId, String title, Long countAnswer, LocalDateTime persistDate) {
        this.questionId = questionId;
        this.title = title;
        this.countAnswer = countAnswer;
        this.persistDate = persistDate;
    }
}
package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.dto.tag.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BookMarksDto {
    private Long questionId;
    private String title;
    private List<TagDto> tags = new ArrayList<>();
    private Long countAnswer;
    private Long countVote;
    private Long countView;
    private LocalDateTime persistQuestionDate;

    public BookMarksDto(Long questionId, String title, Long countAnswer, Long countVote, Long countView, LocalDateTime persistQuestionDate) {
        this.questionId = questionId;
        this.title = title;
        this.countAnswer = countAnswer;
        this.countVote = countVote;
        this.countView = countView;
        this.persistQuestionDate = persistQuestionDate;
    }
}

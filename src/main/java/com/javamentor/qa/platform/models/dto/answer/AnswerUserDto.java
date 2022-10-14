package com.javamentor.qa.platform.models.dto.answer;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor

public class AnswerUserDto implements Serializable {
    private Long answerId;
    private Long questionId;
    private Long countAnswerVote;
    private LocalDateTime persistDate;
    private String body;

    public AnswerUserDto(Long answerId, Long questionId, Long countAnswerVote, String body) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.countAnswerVote = countAnswerVote;
        this.body = body;
    }
}

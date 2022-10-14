package com.javamentor.qa.platform.models.dto.answer;


import com.javamentor.qa.platform.models.dto.comment.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto implements Serializable {
    private Long id;
    private Long userId;
    private Long userReputation;
    private Long questionId;
    private String body;
    private LocalDateTime persistDate;
    private Boolean isHelpful;
    private LocalDateTime dateAccept;
    private Long countValuable;
    private String image;
    private String nickName;
    private String isUserVote;
    private List<CommentDto> comments = new ArrayList<>();

    public AnswerDto(Long id, Long userId, Long userReputation, Long questionId,
                     String body, LocalDateTime persistDate, Boolean isHelpful,
                     LocalDateTime dateAccept, Long countValuable, String image,
                     String nickName, String isUserVote) {
        this.id = id;
        this.userId = userId;
        this.userReputation = userReputation;
        this.questionId = questionId;
        this.body = body;
        this.persistDate = persistDate;
        this.isHelpful = isHelpful;
        this.dateAccept = dateAccept;
        this.countValuable = countValuable;
        this.image = image;
        this.nickName = nickName;
        this.isUserVote = isUserVote;
    }
}

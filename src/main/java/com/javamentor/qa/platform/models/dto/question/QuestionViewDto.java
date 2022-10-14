package com.javamentor.qa.platform.models.dto.question;

import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionViewDto {
    private Long id;
    private String title;
    private Long authorId;
    private String authorName;
    private String authorImage;
    private String description;
    private Long viewCount;
    private Long countAnswer;
    private Long countValuable;
    private Long authorReputation;
    private LocalDateTime persistDateTime;
    private LocalDateTime lastUpdateDateTime;
    private List<TagDto> listTagDto = new ArrayList<>();
    private VoteType isUserVote;
    private Boolean isUserBookmark;
    private Boolean isUserAnswerVote;

    public QuestionViewDto(Long id, String title, Long authorId, String authorName,
                           String authorImage, String description, Long viewCount,
                           Long countAnswer, Long countValuable, Long authorReputation,
                           LocalDateTime persistDateTime, LocalDateTime lastUpdateDateTime,
                           VoteType isUserVote, Boolean isUserBookmark, Boolean isUserAnswerVote) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorImage = authorImage;
        this.description = description;
        this.viewCount = viewCount;
        this.countAnswer = countAnswer;
        this.countValuable = countValuable;
        this.authorReputation = authorReputation;
        this.persistDateTime = persistDateTime;
        this.lastUpdateDateTime = lastUpdateDateTime;
        this.isUserVote = isUserVote;
        this.isUserBookmark = isUserBookmark;
        this.isUserAnswerVote = isUserAnswerVote;
    }
}

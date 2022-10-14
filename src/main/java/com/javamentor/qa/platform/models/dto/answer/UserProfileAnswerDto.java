package com.javamentor.qa.platform.models.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileAnswerDto {
    private Long answerId;
    private String title;
    private Long countVotes;
    private Long questionId;
    private LocalDateTime persistDate;

}



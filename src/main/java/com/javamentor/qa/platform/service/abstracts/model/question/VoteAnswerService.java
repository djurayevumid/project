package com.javamentor.qa.platform.service.abstracts.model.question;

import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

public interface VoteAnswerService extends ReadWriteService<VoteAnswer, Long> {
    Long vote(Long answerId, User user, VoteType voteType);
    boolean isUserNonVoted(Long answerId, Long userId);
}

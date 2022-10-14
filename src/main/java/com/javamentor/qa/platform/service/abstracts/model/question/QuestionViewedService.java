package com.javamentor.qa.platform.service.abstracts.model.question;

import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;


public interface QuestionViewedService extends ReadWriteService<QuestionViewed, Long> {
    boolean checkQuestionViewedByQuestionIdAndUserEmailIsEmpty(Long questionId, String userEmail);

}

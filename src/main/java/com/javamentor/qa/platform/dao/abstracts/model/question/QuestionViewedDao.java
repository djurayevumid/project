package com.javamentor.qa.platform.dao.abstracts.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;

public interface QuestionViewedDao extends ReadWriteDao<QuestionViewed, Long> {
    boolean checkThatQuestionViewByQuestionIdAndUserEmailIsEmpty(Long questionId, String userEmail);
}
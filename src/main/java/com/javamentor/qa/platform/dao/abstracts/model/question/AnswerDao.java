package com.javamentor.qa.platform.dao.abstracts.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;

public interface AnswerDao extends ReadWriteDao<Answer, Long> {
    void deleteByIdByModerator(Long id);
}

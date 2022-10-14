package com.javamentor.qa.platform.service.abstracts.model.question;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

public interface AnswerService extends ReadWriteService<Answer, Long> {
    void deleteByIdByModerator(Long id);
}

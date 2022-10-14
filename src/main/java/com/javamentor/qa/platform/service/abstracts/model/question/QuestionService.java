package com.javamentor.qa.platform.service.abstracts.model.question;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;



public interface QuestionService extends ReadWriteService<Question, Long> {

    Long countQuestions();
}

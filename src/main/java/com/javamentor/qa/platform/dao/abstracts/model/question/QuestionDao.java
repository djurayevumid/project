package com.javamentor.qa.platform.dao.abstracts.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;

import java.util.List;

public interface QuestionDao extends ReadWriteDao<Question, Long> {
    Long countQuestions();
}

package com.javamentor.qa.platform.service.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.dao.abstracts.model.question.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.question.QuestionViewedDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.question.QuestionViewedService;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuestionViewedServiceImpl extends ReadWriteServiceImpl<QuestionViewed, Long> implements QuestionViewedService {

    private final QuestionViewedDao questionViewedDao;

    public QuestionViewedServiceImpl(ReadWriteDao<QuestionViewed, Long> readWriteDao,
                                     QuestionViewedDao questionViewedDao,
                                     QuestionDao questionDao) {
        super(readWriteDao);
        this.questionViewedDao = questionViewedDao;
    }

    @Override
    public boolean checkQuestionViewedByQuestionIdAndUserEmailIsEmpty(Long questionId, String userEmail) {
        return questionViewedDao.checkThatQuestionViewByQuestionIdAndUserEmailIsEmpty(questionId, userEmail);
    }
}

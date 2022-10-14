package com.javamentor.qa.platform.service.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.question.CommentQuestionDao;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.service.abstracts.model.question.CommentQuestionService;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentQuestionServiceImpl extends ReadWriteServiceImpl<CommentQuestion, Long> implements CommentQuestionService {
    public CommentQuestionServiceImpl(CommentQuestionDao commentQuestionDao) {
        super(commentQuestionDao);
    }
}

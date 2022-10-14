package com.javamentor.qa.platform.service.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.question.CommentAnswerDao;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.service.abstracts.model.question.CommentAnswerService;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentAnswerServiceImpl extends ReadWriteServiceImpl<CommentAnswer, Long> implements CommentAnswerService {

    private final CommentAnswerDao commentAnswerDao;

    public CommentAnswerServiceImpl(CommentAnswerDao commentAnswerDao) {
        super(commentAnswerDao);
        this.commentAnswerDao = commentAnswerDao;
    }
}

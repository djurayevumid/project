package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.models.dto.answer.AnswerDto;
import com.javamentor.qa.platform.models.dto.answer.AnswerUserDto;
import com.javamentor.qa.platform.models.dto.comment.CommentDto;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnswerDtoServiceImpl implements AnswerDtoService {

    private final AnswerDtoDao answerDtoDao;
    private final CommentDtoDao commentDtoDao;

    @Autowired
    public AnswerDtoServiceImpl(AnswerDtoDao answerDtoDao, CommentDtoDao commentAnswerDtoDao) {
        this.answerDtoDao = answerDtoDao;
        this.commentDtoDao = commentAnswerDtoDao;
    }

    @Override
    public List<AnswerDto> getListAnswersOfQuestionId(Long id) {
        List<AnswerDto> answerDtoList = answerDtoDao.getListAnswersOfQuestionId(id);

        List<Long> answersIdList = answerDtoList.stream()
                .map(AnswerDto::getId)
                .collect(Collectors.toList());

        Map<Long, List<CommentDto>> commentsDtoByAnswersIds = commentDtoDao.getCommentsDtoByAnswersIds(answersIdList);

        answerDtoList.forEach(answerDto -> answerDto.setComments(
                commentsDtoByAnswersIds.get(answerDto.getId()) != null ?
                        commentsDtoByAnswersIds.get(answerDto.getId()) :
                        new ArrayList<>()));

        return answerDtoList;
    }

    @Override
    public List<AnswerUserDto> getAnswerPerWeek(Long id) {
        return answerDtoDao.getAnswerUserPerWeek(id);
    }
}

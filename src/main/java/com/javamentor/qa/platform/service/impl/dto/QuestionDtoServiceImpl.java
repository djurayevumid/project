package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.page.PageDto;
import com.javamentor.qa.platform.models.dto.question.QuestionCommentDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionViewDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionDtoServiceImpl extends PageDtoServiceImpl<QuestionViewDto> implements QuestionDtoService {

    private final QuestionDtoDao questionDao;
    private final TagDtoDao tagDtoDao;
    private  final CommentDtoDao commentDtoDao;
    private final AnswerDtoDao answerDtoDao;

    public QuestionDtoServiceImpl(QuestionDtoDao questionDao, TagDtoDao tagDtoDao, CommentDtoDao commentDtoDao, AnswerDtoDao answerDtoDao) {
        this.questionDao = questionDao;
        this.tagDtoDao = tagDtoDao;
        this.commentDtoDao = commentDtoDao;
        this.answerDtoDao = answerDtoDao;
    }

    @Override
    @Transactional
    public PageDto<QuestionViewDto> getPage(int currentPageNumber, int itemsOnPage, Map<Object, Object> map) {

        User userAuth =(User) map.get("userAuth");
        List<Long> trackedTagsList = tagDtoDao.getTrackedTagsByUserId(userAuth.getId())
                                     .stream()
                                     .map(TagDto::getId)
                                     .collect(Collectors.toList());
        if (trackedTagsList.isEmpty()) {
            trackedTagsList = null;
        }
        List<Long> ignoredTagsList = tagDtoDao.getIgnoredTagsByUserId(userAuth.getId())
                                     .stream()
                                     .map(TagDto::getId)
                                     .collect(Collectors.toList());
        if (ignoredTagsList.isEmpty()) {
            ignoredTagsList = null;
        }
        map.put("trackedIds", trackedTagsList);
        map.put("ignoredIds", ignoredTagsList);

        PageDto<QuestionViewDto> pageDto = super.getPage(currentPageNumber, itemsOnPage, map);
        List<QuestionViewDto> questionViewDtos = pageDto.getItems();

        List<Long> questionIds = questionViewDtos.stream()
                .map(QuestionViewDto::getId)
                .collect(Collectors.toList());

        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getMapTagsByQuestionIds(questionIds);
        for (QuestionViewDto questionViewDto : questionViewDtos) {
            questionViewDto.setListTagDto(tagsMap.get(questionViewDto.getId()));
        }
        pageDto.setItems(questionViewDtos);
        return pageDto;
    }

    @Override
    @Transactional
    public Optional<QuestionDto> getQuestionDtoByIdAndUserAuthId(long id, long userId) {
        Optional<QuestionDto> questionDto = questionDao.getQuestionDtoByIdAndUserAuthId(id, userId);
        questionDto.ifPresent(dto -> dto.setListTagDto(tagDtoDao.getTagDtoListByQuestionId(id)));
        questionDto.ifPresent(dto -> dto.setListCommentsDto(commentDtoDao.getCommentDtoListByQuestionId(id)));
        questionDto.ifPresent(dto -> dto.setListAnswerDto(answerDtoDao.getListAnswersOfQuestionId(id)));
        return questionDto;
    }
}

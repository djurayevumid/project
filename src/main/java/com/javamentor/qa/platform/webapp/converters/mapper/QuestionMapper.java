package com.javamentor.qa.platform.webapp.converters.mapper;


import com.javamentor.qa.platform.models.dto.comment.CommentDto;
import com.javamentor.qa.platform.models.dto.question.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring", uses = {TagMapper.class})
public abstract class QuestionMapper {

    @Mapping(source = "question.tags", target = "listTagDto")
    @Mapping(source = "question.answers", target = "listAnswerDto")
    @Mapping(source = "question.commentQuestions",
            target = "listCommentsDto", qualifiedByName = "CommentQuestionToCommentDto")
    @Mapping(source = "question.id", target = "id")
    @Mapping(source = "question.persistDateTime", target = "persistDateTime")
    @Mapping(source = "question.lastUpdateDateTime", target = "lastUpdateDateTime")
    @Mapping(target = "countAnswer", constant = "0L")
    @Mapping(target = "countValuable", constant = "0L")
    @Mapping(target = "viewCount", constant = "0L")
    @Mapping(source = "question.user.id", target = "authorId")
    @Mapping(source = "question.user.fullName", target = "authorName")
    @Mapping(source = "question.user.imageLink", target = "authorImage")
    @Mapping(target = "authorReputation", constant = "0L")
    @Mapping(target = "isUserAnswerVote", constant = "false")
    @Mapping(target = "isUserBookmark", constant = "false")
    public abstract QuestionDto persistConvertToDto(Question question);

    @Named("CommentQuestionToCommentDto")
    public static List<CommentDto> CommentQuestionToCommentDto(List<CommentQuestion> commentQuestions) {
        return new ArrayList<CommentDto>();
    }

    public abstract Question toModel(QuestionCreateDto questionCreateDto);

}

package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.question.UserProfileQuestionDto;

import java.util.List;
import java.util.Optional;

public interface QuestionDtoDao {

    Optional<QuestionDto> getQuestionDtoByIdAndUserAuthId(long id, long userId);

}

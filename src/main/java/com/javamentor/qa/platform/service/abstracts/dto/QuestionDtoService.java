package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionViewDto;

import java.util.Optional;

public interface QuestionDtoService extends PageDtoService<QuestionViewDto> {

    Optional<QuestionDto> getQuestionDtoByIdAndUserAuthId(long id, long userId);
}

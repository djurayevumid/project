package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.answer.AnswerDto;
import com.javamentor.qa.platform.models.dto.answer.AnswerUserDto;

import java.util.List;

public interface AnswerDtoService {
    List<AnswerDto> getListAnswersOfQuestionId(Long questionId);
    List<AnswerUserDto> getAnswerPerWeek(Long id);
}

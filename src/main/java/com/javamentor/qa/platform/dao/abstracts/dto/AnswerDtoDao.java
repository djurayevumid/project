package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.answer.AnswerDto;
import com.javamentor.qa.platform.models.dto.answer.AnswerUserDto;

import java.util.List;

public interface AnswerDtoDao {
    List<AnswerDto> getListAnswersOfQuestionId(Long id);
    List<AnswerUserDto> getAnswerUserPerWeek(Long id);

}

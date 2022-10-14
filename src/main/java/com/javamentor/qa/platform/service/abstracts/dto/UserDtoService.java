package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.answer.UserProfileAnswerDto;
import com.javamentor.qa.platform.models.dto.BookMarksDto;
import com.javamentor.qa.platform.models.dto.question.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.models.dto.user.UserSupplierDto;

import java.util.List;
import java.util.Optional;

public interface UserDtoService extends PageDtoService<UserDto> {

    Optional<UserSupplierDto> getUserDtoById(Long id);
    List<UserProfileAnswerDto> getAnswersDtoByUserId(Long id);
    List<BookMarksDto> getBookMarksDtoByUserId(Long id);
    List<UserProfileQuestionDto> getDeletedUserProfileQuestionDtos(Long id);
    List<UserProfileQuestionDto> getAllUserProfileQuestionDtoByUserId(long id);

    List<UserDto> getTop10Users();
    List<UserDto>  getTop10UsersByAnswersLastMonth();
    List<UserDto> getTop10UsersByAnswersLastYear();
    Long getCountAnswersByWeek(Long id);

}

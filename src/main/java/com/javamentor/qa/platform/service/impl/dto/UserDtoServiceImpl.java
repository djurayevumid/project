package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.models.dto.BookMarksDto;
import com.javamentor.qa.platform.models.dto.answer.UserProfileAnswerDto;

import com.javamentor.qa.platform.models.dto.page.PageDto;
import com.javamentor.qa.platform.models.dto.question.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.models.dto.user.UserSupplierDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDtoServiceImpl extends PageDtoServiceImpl<UserDto> implements UserDtoService {

    private final TagDtoDao tagDtoDao;
    private final UserDtoDao userDtoDao;

    public UserDtoServiceImpl(TagDtoDao tagDtoDao, UserDtoDao userDtoDao) {
        this.tagDtoDao = tagDtoDao;
        this.userDtoDao = userDtoDao;
    }

    @Override
    @Transactional
    public PageDto<UserDto> getPage(int currentPageNumber, int itemsOnPage, Map<Object, Object> map) {

        PageDto<UserDto> pageDto = super.getPage(currentPageNumber, itemsOnPage, map);
        List<UserDto> userDtos = pageDto.getItems();

        List<Long> userIds = userDtos.stream()
                .map(UserDto::getId)
                .collect(Collectors.toList());

        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getMapTagsByUserIds(userIds);
        for (UserDto userDto : userDtos) {
            userDto.setTags(tagsMap.get(userDto.getId()));
        }

        pageDto.setItems(userDtos);
        return pageDto;
    }

    @Override
    @Transactional
    public Optional<UserSupplierDto> getUserDtoById(Long id) {
        return userDtoDao.getUserDtoById(id);
    }

    @Override
    @Transactional
    public List<BookMarksDto> getBookMarksDtoByUserId(Long id) {
        List<BookMarksDto> bmDtoList = userDtoDao.getBookMarksDtoByUserId(id);
        List<Long> qIds = bmDtoList.stream().map(BookMarksDto::getQuestionId).collect(Collectors.toList());
        Map<Long, List<TagDto>> tagMap = tagDtoDao.getMapTagsByQuestionIds(qIds);
        bmDtoList.forEach(bm -> bm.setTags(tagMap.get(bm.getQuestionId())));
        return bmDtoList;
    }

    @Override
    @Transactional
    public List<UserProfileAnswerDto> getAnswersDtoByUserId(Long id) {
        return userDtoDao.getUserAnswersDtoById(id);
    }

    @Override
    public List<UserProfileQuestionDto> getDeletedUserProfileQuestionDtos(Long id) {
        List<UserProfileQuestionDto> qDtos = userDtoDao.getDeletedUserProfileQuestionDtos(id);
        List<Long> qIds = qDtos.stream().map(UserProfileQuestionDto::getQuestionId).collect(Collectors.toList());
        Map<Long, List<TagDto>> tagMap = tagDtoDao.getMapTagsByQuestionIds(qIds);
        qDtos.forEach(q -> q.setTags(tagMap.get(q.getQuestionId())));
        return qDtos;
    }

    @Override
    @Transactional
    public List<UserProfileQuestionDto> getAllUserProfileQuestionDtoByUserId(long id) {
        List<UserProfileQuestionDto> userProfileQuestionDtos = userDtoDao.getAllUserProfileQuestionDtoByUserId(id);
        List<Long> qIds = userProfileQuestionDtos.stream().map(UserProfileQuestionDto::getQuestionId).collect(Collectors.toList());
        Map<Long, List<TagDto>> tagMap = tagDtoDao.getMapTagsByQuestionIds(qIds);
        userProfileQuestionDtos.forEach(dto -> dto.setTags(tagMap.get(dto.getQuestionId())));
        return userProfileQuestionDtos;
    }

    @Override
    @Transactional
    public List<UserDto> getTop10Users() {
        List<UserDto> userDtos = userDtoDao.getTop10Users();
        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getMapTop3TagsByUsersIds(userDtos.stream().map(UserDto::getId).collect(Collectors.toList()));
        userDtos.forEach(dto -> dto.setTags(tagsMap.get(dto.getId())));
        return userDtos;
    }

    @Override
    @Transactional
    public List<UserDto> getTop10UsersByAnswersLastMonth() {
        List<UserDto> userDtosLastMonth = userDtoDao.getTop10UsersByAnswersLastMonth();
        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getMapTop3TagsByUsersIds(userDtosLastMonth.stream().map(UserDto::getId).collect(Collectors.toList()));
        userDtosLastMonth.forEach(dto -> dto.setTags(tagsMap.get(dto.getId())));
        return userDtosLastMonth;
    }

    @Override
    @Transactional
    public List<UserDto> getTop10UsersByAnswersLastYear() {
        List<UserDto> userDtosLastYear = userDtoDao.getTop10UsersByAnswersLastYear();
        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getMapTop3TagsByUsersIds(userDtosLastYear.stream().map(UserDto::getId).collect(Collectors.toList()));
        userDtosLastYear.forEach(dto -> dto.setTags(tagsMap.get(dto.getId())));
        return userDtosLastYear;
    }
    @Override
    @Transactional
    public Long getCountAnswersByWeek(Long id) {
        return userDtoDao.getCountAnswersByWeek(id);
    }

}
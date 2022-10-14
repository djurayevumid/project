package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;

import java.util.List;
import java.util.Map;

public interface TagDtoDao {

    List<TagDto> getTrackedTagsByUserId(Long id);

    List<TagDto> getIgnoredTagsByUserId(Long id);

    List<RelatedTagsDto> getRelatedTagsDto();

    List<TagDto> getTagsByLetters(String letters);

    Map<Long, List<TagDto>> getMapTagsByQuestionIds(List<Long> questionIds);

    List<TagDto> getTagDtoListByQuestionId(Long id);

    Map<Long, List<TagDto>> getMapTagsByUserIds(List<Long> userIds);

    Boolean checkIfConfuseIgnoredAndTrackedTags(Long userId, List<Long> ignoredTags, List<Long> trackedTags);

    Map<Long, List<TagDto>> getMapTop3TagsByUsersIds(List<Long> usersIds);
}

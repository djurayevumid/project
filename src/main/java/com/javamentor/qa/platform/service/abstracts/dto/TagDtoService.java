package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;

import java.util.List;

public interface TagDtoService extends PageDtoService<TagDto> {

    List<TagDto> getTagsByLetters(String letters);

    List<TagDto> getTrackedTagsByUserId(Long id);

    List<TagDto> getIgnoredTagsByUserId(Long id);

    List<RelatedTagsDto> getRelatedTagsDto();

    Boolean checkIfConfuseIgnoredAndTrackedTags(Long userId, List<Long> ignoredTags, List<Long> trackedTags);
}

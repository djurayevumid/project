package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagDtoServiceImpl extends PageDtoServiceImpl<TagDto> implements TagDtoService {

    private final TagDtoDao tagDao;

    public TagDtoServiceImpl(TagDtoDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<TagDto> getTrackedTagsByUserId(Long id) {
        return tagDao.getTrackedTagsByUserId(id);
    }

    @Override
    public List<TagDto> getIgnoredTagsByUserId(Long id) {
        return tagDao.getIgnoredTagsByUserId(id);
    }

    @Override
    @Transactional
    public List<RelatedTagsDto> getRelatedTagsDto() {
        return tagDao.getRelatedTagsDto();
    }

    @Override
    public Boolean checkIfConfuseIgnoredAndTrackedTags(Long userId, List<Long> ignoredTags, List<Long> trackedTags) {
        return tagDao.checkIfConfuseIgnoredAndTrackedTags(userId, ignoredTags, trackedTags);
    }

    @Override
    public List<TagDto> getTagsByLetters(String letters) {
        return tagDao.getTagsByLetters(letters);
    }
}


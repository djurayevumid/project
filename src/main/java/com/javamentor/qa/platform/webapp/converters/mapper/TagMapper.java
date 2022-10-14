package com.javamentor.qa.platform.webapp.converters.mapper;

import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDto toDto(Tag tag);

    Tag toModel(TagDto tagDto);

}

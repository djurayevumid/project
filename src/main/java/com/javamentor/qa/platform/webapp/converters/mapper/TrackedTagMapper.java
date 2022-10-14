package com.javamentor.qa.platform.webapp.converters.mapper;

import com.javamentor.qa.platform.models.dto.tag.TrackedTagDto;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class TrackedTagMapper {

    @Mapping(source = "trackedTag.id", target = "id")
    @Mapping(source = "trackedTag.name", target = "name")
    public abstract TrackedTagDto persistConvertToDto(TrackedTag trackedTag);
}

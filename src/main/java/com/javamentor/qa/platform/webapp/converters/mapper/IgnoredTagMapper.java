package com.javamentor.qa.platform.webapp.converters.mapper;

import com.javamentor.qa.platform.models.dto.tag.IgnoredTagDto;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class IgnoredTagMapper {

    @Mapping(source = "ignoredTag.id", target = "id")
    @Mapping(source = "ignoredTag.name", target = "name")
    public abstract IgnoredTagDto persistConvertToDto(IgnoredTag ignoredTag);
}

package com.javamentor.qa.platform.service.abstracts.model.question;

import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagService extends ReadWriteService<Tag, Long> {
    boolean existsByName(String name);

    Optional<Tag> getByName(String name);

    Optional<Tag> getById(Long id);

    List<Tag> getByAllNames(Collection<String> names);
}

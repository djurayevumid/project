package com.javamentor.qa.platform.dao.abstracts.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagDao extends ReadWriteDao<Tag, Long> {
    boolean existsByName(String name);

    Optional<Tag> getByName(String name);

    List<Tag> getByAllNames(Collection<String> names);
}

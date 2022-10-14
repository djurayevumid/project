package com.javamentor.qa.platform.dao.abstracts.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;

import java.util.Optional;

public interface IgnoredTagDao extends ReadWriteDao<IgnoredTag, Long> {

    Optional<IgnoredTag> getIgnoredTagByUser(Long userId, Long tagId);
}

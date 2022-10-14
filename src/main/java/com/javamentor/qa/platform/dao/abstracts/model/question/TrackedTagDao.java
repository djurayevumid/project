package com.javamentor.qa.platform.dao.abstracts.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;

import java.util.Optional;

public interface TrackedTagDao extends ReadWriteDao<TrackedTag, Long> {

    Optional<TrackedTag> getTrackedTagByUser(Long userId, Long tagId);
}

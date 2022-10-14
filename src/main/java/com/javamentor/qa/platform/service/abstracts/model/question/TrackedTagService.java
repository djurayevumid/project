package com.javamentor.qa.platform.service.abstracts.model.question;

import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

import java.util.Optional;

public interface TrackedTagService extends ReadWriteService<TrackedTag, Long> {

    Optional<TrackedTag> getTrackedTagByUser(Long userId, Long tagId);
}

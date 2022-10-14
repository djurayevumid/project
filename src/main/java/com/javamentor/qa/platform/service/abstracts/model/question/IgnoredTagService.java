package com.javamentor.qa.platform.service.abstracts.model.question;

import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

import java.util.Optional;

public interface IgnoredTagService extends ReadWriteService<IgnoredTag, Long> {

    Optional<IgnoredTag> getIgnoredTagByUser(Long userId, Long tagId);
}

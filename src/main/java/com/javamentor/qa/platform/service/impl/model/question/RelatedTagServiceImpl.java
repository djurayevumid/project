package com.javamentor.qa.platform.service.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.question.RelatedTagDao;
import com.javamentor.qa.platform.models.entity.question.RelatedTag;
import com.javamentor.qa.platform.service.abstracts.model.question.RelatedTagService;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RelatedTagServiceImpl extends ReadWriteServiceImpl<RelatedTag, Long> implements RelatedTagService {
    public RelatedTagServiceImpl(RelatedTagDao relatedTagDao) {
        super(relatedTagDao);
    }
}

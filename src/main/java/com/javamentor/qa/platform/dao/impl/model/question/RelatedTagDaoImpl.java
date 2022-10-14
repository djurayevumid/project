package com.javamentor.qa.platform.dao.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.question.RelatedTagDao;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.RelatedTag;
import org.springframework.stereotype.Repository;

@Repository
public class RelatedTagDaoImpl extends ReadWriteDaoImpl<RelatedTag, Long> implements RelatedTagDao {
}

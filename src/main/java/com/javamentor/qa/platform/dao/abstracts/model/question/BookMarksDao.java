package com.javamentor.qa.platform.dao.abstracts.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.BookMarks;

public interface BookMarksDao extends ReadWriteDao<BookMarks, Long> {
    boolean bookmarkIsExist(Long questionId, Long userId);
}

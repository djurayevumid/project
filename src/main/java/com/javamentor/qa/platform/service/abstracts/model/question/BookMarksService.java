package com.javamentor.qa.platform.service.abstracts.model.question;

import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;


public interface BookMarksService extends ReadWriteService<BookMarks, Long> {

    boolean isBookmarkExist(Long questionId, Long userId);
}
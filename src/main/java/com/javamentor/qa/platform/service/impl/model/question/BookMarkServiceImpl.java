package com.javamentor.qa.platform.service.impl.model.question;

import com.javamentor.qa.platform.dao.abstracts.model.question.BookMarksDao;
import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.service.abstracts.model.question.BookMarksService;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BookMarkServiceImpl extends ReadWriteServiceImpl<BookMarks, Long> implements BookMarksService {

    final private BookMarksDao bookMarksDao;

    public BookMarkServiceImpl(BookMarksDao bookMarksDao) {
        super(bookMarksDao);
        this.bookMarksDao = bookMarksDao;
    }

    @Override
    public boolean isBookmarkExist(Long questionId, Long userId) {
        return bookMarksDao.bookmarkIsExist(questionId, userId);
    }
}


package com.javamentor.qa.platform.service.impl.model.user;

import com.javamentor.qa.platform.dao.abstracts.model.user.UserChatPinDao;
import com.javamentor.qa.platform.models.entity.user.UserChatPin;
import com.javamentor.qa.platform.service.abstracts.model.user.UserChatPinService;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserChatPinServiceImpl extends ReadWriteServiceImpl<UserChatPin, Long> implements UserChatPinService {
    private final UserChatPinDao userChatPinDao;

    public UserChatPinServiceImpl(UserChatPinDao userChatPinDao) {
        super(userChatPinDao);
        this.userChatPinDao = userChatPinDao;
    }
}

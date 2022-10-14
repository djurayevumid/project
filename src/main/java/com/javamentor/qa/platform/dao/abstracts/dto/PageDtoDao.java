package com.javamentor.qa.platform.dao.abstracts.dto;

import java.util.List;
import java.util.Map;

public interface PageDtoDao<T> {
    List<T> getItems(Map<Object, Object> param);
    long getTotalResultCount(Map<Object, Object> param);
}

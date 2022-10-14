package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.page.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.PageDtoService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@NoArgsConstructor
public class PageDtoServiceImpl<T> implements PageDtoService<T> {

    public Map<String, PageDtoDao<?>> pageDtoDaoMap;

    @Autowired
    public void setPageDtoDaoMap(Map<String, PageDtoDao<?>> pageDtoDaoMap) {
        this.pageDtoDaoMap = pageDtoDaoMap;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public PageDto<T> getPage(int currentPageNumber, int itemsOnPage, Map<Object, Object> map) {
        final int TOTAL_PAGE_COUNT_DEFAULT = 1;
        PageDtoDao<T> pageDtoDao = (PageDtoDao<T>) pageDtoDaoMap.get((String) map.get("class"));
        map.put("currentPageNumber", currentPageNumber);
        map.put("itemsOnPage", itemsOnPage);
        long totalResultCount = pageDtoDao.getTotalResultCount(map);
        int totalPageCount = (int) Math.ceil((double) totalResultCount / itemsOnPage);
        totalPageCount = totalPageCount > 0 ? totalPageCount : TOTAL_PAGE_COUNT_DEFAULT;

        return new PageDto<>(currentPageNumber, totalPageCount,
                itemsOnPage, totalResultCount, pageDtoDao.getItems(map));
    }

}

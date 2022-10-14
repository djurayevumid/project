package com.javamentor.qa.platform.models.dto.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDto <T>  implements Serializable {

    private int currentPageNumber;
    private int totalPageCount;
    private int itemsOnPage;
    private long totalResultCount;
    private List<T> items;
}

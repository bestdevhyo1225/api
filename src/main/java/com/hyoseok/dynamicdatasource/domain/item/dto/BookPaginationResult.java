package com.hyoseok.dynamicdatasource.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BookPaginationResult {
    private final List<BookSearchResult> books;
    private final int pageNumber;
    private final int pageSize;
    private final long totalCount;
}

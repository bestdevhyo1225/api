package com.hyoseok.dynamicdatasource.usecase.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookPaginationResult {
    private List<BookResult> books;
    private int pageNumber;
    private int pageSize;
    private long totalCount;
}

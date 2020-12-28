package com.hyoseok.dynamicdatasource.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BookDetailSearchResult {
    private final Long bookId;
    private final String title;
    private final String author;
    private final int price;
    private final String contents;
    private final List<BookImageSearchResult> images;
}

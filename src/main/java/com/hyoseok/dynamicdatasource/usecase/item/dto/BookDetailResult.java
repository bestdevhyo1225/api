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
public class BookDetailResult {
    private Long bookId;
    private String title;
    private String author;
    private int price;
    private String contents;
    private List<BookImageSearchResult> images;
}

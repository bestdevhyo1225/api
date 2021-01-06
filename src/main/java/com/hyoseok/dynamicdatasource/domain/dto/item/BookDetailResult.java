package com.hyoseok.dynamicdatasource.domain.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailSearchResult {
    private Long bookId;
    private String title;
    private String author;
    private int price;
    private String contents;
    private List<BookImageSearchResult> images;
}

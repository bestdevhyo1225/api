package com.hyoseok.dynamicdatasource.domain.dto.item;

import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchResult {
    private Long bookId;
    private String title;
    private String author;
    private int price;
}

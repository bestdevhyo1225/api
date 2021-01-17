package com.hyoseok.dynamicdatasource.usecase.item.dto;

import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResult {
    private Long bookId;
    private String title;
    private String author;
    private int price;
}

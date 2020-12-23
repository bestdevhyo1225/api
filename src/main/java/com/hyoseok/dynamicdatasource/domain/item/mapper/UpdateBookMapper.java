package com.hyoseok.dynamicdatasource.domain.item.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateBookMapper {
    private final Long bookId;
    private final String title;
    private final String author;
    private final int price;
}

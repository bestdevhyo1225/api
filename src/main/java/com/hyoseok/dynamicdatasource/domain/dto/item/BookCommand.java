package com.hyoseok.dynamicdatasource.domain.dto.item;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class BookCommand {
    private final Long bookId;
    private final String title;
    private final String author;
    private final int price;
}

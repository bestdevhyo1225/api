package com.hyoseok.dynamicdatasource.domain.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookModificationResult {
    private final String title;
    private final String author;
    private final int price;
}

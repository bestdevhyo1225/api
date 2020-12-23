package com.hyoseok.dynamicdatasource.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdatedBookDto {
    private final String title;
    private final String author;
    private final int price;
}

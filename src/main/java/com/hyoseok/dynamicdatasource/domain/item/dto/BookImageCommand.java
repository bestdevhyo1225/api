package com.hyoseok.dynamicdatasource.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookImageCommand {
    private final String kinds;
    private final String imageUrl;
    private final int sortOrder;
}

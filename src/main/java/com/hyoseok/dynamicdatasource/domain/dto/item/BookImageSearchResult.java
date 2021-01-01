package com.hyoseok.dynamicdatasource.domain.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookImageSearchResult {
    private final Long imageId;
    private final String imageUrl;
}

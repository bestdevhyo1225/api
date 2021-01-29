package com.hyoseok.dynamicdatasource.usecase.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookImageSearchResult {
    private String kinds;
    private String imageUrl;
    private int sortOrder;
}

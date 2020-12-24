package com.hyoseok.dynamicdatasource.domain.item.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateBookImageMapper {
    private final String kinds;
    private final String imageUrl;
    private final int sortOrder;
}

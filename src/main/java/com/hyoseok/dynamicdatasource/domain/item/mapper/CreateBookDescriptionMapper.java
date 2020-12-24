package com.hyoseok.dynamicdatasource.domain.item.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateBookDescriptionMapper {
    private final String contents;
}

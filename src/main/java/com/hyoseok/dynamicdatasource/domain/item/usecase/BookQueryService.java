package com.hyoseok.dynamicdatasource.domain.item.usecase;

import com.hyoseok.dynamicdatasource.domain.item.dto.FindBookDto;

import java.util.List;

public interface BookQueryService {
    FindBookDto findBook(Long bookId);
    List<FindBookDto> findBooks();
}

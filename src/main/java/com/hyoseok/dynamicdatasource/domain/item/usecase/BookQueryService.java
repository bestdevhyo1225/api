package com.hyoseok.dynamicdatasource.domain.item.usecase;

import com.hyoseok.dynamicdatasource.domain.item.dto.FindBookDetailDto;
import com.hyoseok.dynamicdatasource.domain.item.dto.FindBookDto;

import java.util.List;

public interface BookQueryService {
    FindBookDetailDto findBookLeftJoin(Long bookId);
    FindBookDto findBook(Long bookId);
    List<FindBookDto> findBooks();
}

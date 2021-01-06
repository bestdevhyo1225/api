package com.hyoseok.dynamicdatasource.domain.usecase.item;

import com.hyoseok.dynamicdatasource.domain.dto.item.BookDetailResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookPaginationResult;

public interface BookQueryService {
    BookDetailResult findBookLeftJoin(Long bookId);
    BookPaginationResult findBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize);
    BookResult findBook(Long bookId);
}

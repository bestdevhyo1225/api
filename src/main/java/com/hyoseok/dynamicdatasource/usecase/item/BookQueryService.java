package com.hyoseok.dynamicdatasource.usecase.item;

import com.hyoseok.dynamicdatasource.usecase.item.dto.BookDetailResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookPaginationResult;

public interface BookQueryService {
    BookDetailResult findBookLeftJoin(Long bookId);
    BookPaginationResult findBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize);
    BookResult findBook(Long bookId);
}

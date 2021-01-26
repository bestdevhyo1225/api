package com.hyoseok.dynamicdatasource.usecase.item;

import com.hyoseok.dynamicdatasource.usecase.item.dto.BookDetailResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookPaginationResult;

public interface BookQueryService {
    BookPaginationResult findBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize);
    BookPaginationResult fallbackBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize);
    BookDetailResult findBookLeftJoin(Long bookId);
    BookDetailResult fallbackBookLeftJoin(Long bookId);
    BookResult findBook(Long bookId);
    BookResult fallbackBook(Long bookId);
}

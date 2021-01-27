package com.hyoseok.dynamicdatasource.usecase.item;

import com.hyoseok.dynamicdatasource.usecase.item.dto.BookDetailResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookPaginationResult;

public interface BookQueryService {
    BookPaginationResult findBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize);
    BookDetailResult findBookDetail(Long bookId);
    BookResult findBook(Long bookId);
}

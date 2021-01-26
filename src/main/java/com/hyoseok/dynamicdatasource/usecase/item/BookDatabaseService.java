package com.hyoseok.dynamicdatasource.usecase.item;

import com.hyoseok.dynamicdatasource.usecase.item.dto.BookDetailResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookPaginationResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookResult;

public interface BookDatabaseService {
    BookPaginationResult findBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize);
    BookDetailResult findBookLeftJoin(Long bookId);
    BookResult findBook(Long bookId);
}

package com.hyoseok.dynamicdatasource.domain.item.usecase;

import com.hyoseok.dynamicdatasource.domain.item.dto.BookDetailSearchResult;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookSearchResult;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookPaginationResult;

public interface BookQueryService {
    BookDetailSearchResult findBookLeftJoin(Long bookId);
    BookPaginationResult findBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize);
    BookSearchResult findBook(Long bookId);
}

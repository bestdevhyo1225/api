package com.hyoseok.dynamicdatasource.domain.usecase.item;

import com.hyoseok.dynamicdatasource.domain.dto.item.BookDetailSearchResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookSearchResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookPaginationResult;

public interface BookQueryService {
    BookDetailSearchResult findBookLeftJoin(Long bookId);
    BookPaginationResult findBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize);
    BookSearchResult findBook(Long bookId);
}

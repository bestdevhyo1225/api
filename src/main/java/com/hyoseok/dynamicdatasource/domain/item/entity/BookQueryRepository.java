package com.hyoseok.dynamicdatasource.domain.item.entity;

import com.hyoseok.dynamicdatasource.domain.item.dto.BookSearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookQueryRepository {
    Optional<Book> findBookLeftJoinExcludeDescription(Long bookId);
    Optional<Book> findBookLeftJoin(Long bookId);
    Page<BookSearchResult> findBooksByPagination(Pageable pageable, boolean useSearchBtn);
}

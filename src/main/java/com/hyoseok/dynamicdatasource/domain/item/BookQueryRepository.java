package com.hyoseok.dynamicdatasource.domain.item;

import com.hyoseok.dynamicdatasource.usecase.item.dto.BookResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookQueryRepository {
    Optional<Book> findBookLeftJoinExcludeDescription(Long bookId);
    Optional<Book> findBookLeftJoin(Long bookId);
    Page<BookResult> findBooksByPagination(Pageable pageable, boolean useSearchBtn);
}

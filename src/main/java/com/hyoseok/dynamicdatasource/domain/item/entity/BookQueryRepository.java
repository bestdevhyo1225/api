package com.hyoseok.dynamicdatasource.domain.item.entity;

import java.util.Optional;

public interface BookQueryRepository {
    Optional<Book> findBookLeftJoinExcludeDescription(Long bookId);
    Optional<Book> findBookLeftJoin(Long bookId);
}

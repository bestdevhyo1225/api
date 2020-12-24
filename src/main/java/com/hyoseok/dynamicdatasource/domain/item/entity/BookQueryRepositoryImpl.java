package com.hyoseok.dynamicdatasource.domain.item.entity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.hyoseok.dynamicdatasource.domain.item.entity.QBook.*;

@Repository
@RequiredArgsConstructor
public class BookQueryRepositoryImpl implements BookQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Book> findBookLeftJoinExcludeDescription(Long bookId) {
        Book findBook = queryFactory
                .selectFrom(book)
                .leftJoin(book.bookImages).fetchJoin()
                .where(bookIdEq(bookId))
                .fetchOne();

        return Optional.ofNullable(findBook);
    }

    @Override
    public Optional<Book> findBookLeftJoin(Long bookId) {
        Book findBook = queryFactory
                .selectFrom(book)
                .leftJoin(book.bookImages).fetchJoin()
                .leftJoin(book.bookDescription).fetchJoin()
                .where(bookIdEq(bookId))
                .fetchOne();

        return Optional.ofNullable(findBook);
    }

    public BooleanExpression bookIdEq(Long bookId) {
        return book.id.eq(bookId);
    }
}

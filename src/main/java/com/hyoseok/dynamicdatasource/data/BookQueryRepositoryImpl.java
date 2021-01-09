package com.hyoseok.dynamicdatasource.data;

import com.hyoseok.dynamicdatasource.domain.dto.item.BookResult;
import com.hyoseok.dynamicdatasource.domain.entity.item.Book;
import com.hyoseok.dynamicdatasource.domain.entity.item.BookQueryRepository;
import com.hyoseok.dynamicdatasource.domain.entity.item.QBook;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

import static com.hyoseok.dynamicdatasource.domain.entity.item.QBook.*;

@Repository
@Slf4j
public class BookQueryRepositoryImpl extends QuerydslRepositorySupport implements BookQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BookQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Book.class);
        this.queryFactory = queryFactory;
    }

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

    @Override
    public Page<BookResult> findBooksByPagination(Pageable pageable, boolean useSearchBtn) {
        JPAQuery<BookResult> query = queryFactory
                .select(
                        Projections.constructor(
                                BookResult.class,
                                book.id,
                                book.title,
                                book.author,
                                book.price
                        )
                )
                .from(book)
                .orderBy(book.id.desc());

        JPQLQuery<BookResult> pagination = querydsl().applyPagination(pageable, query);

        // 1. 검색 버튼을 사용한 경우, count 쿼리를 실행하지 않는다. 대신 totalCount = 100을 반환한다.
        if (useSearchBtn) {
            int fixedPageSize = 10;
            int fixedTotalCount = fixedPageSize * pageable.getPageSize();
            return new PageImpl<>(pagination.fetch(), pageable, fixedTotalCount);
        }

        // 2. 페이지 버튼을 사용한 경우, count 쿼리를 실행한다.
        long totalCount = pagination.fetchCount();

        /*
        * 전체 데이터 수를 초과한 요청이 있을 수 있기 때문에 PageRequest를 보정한다.
        * 전체 데이터 수 47개, 요청한 pageNumber가 7이라면, 71 ~ 80번의 데이터를 가져와야 하는데 없기 때문에 보정을 해줘야 한다.
        * */
        Pageable pageRequest = new CorrectedPageRequest(pageable, totalCount);

        return new PageImpl<>(querydsl().applyPagination(pageRequest, query).fetch(), pageRequest, totalCount);
    }

    private BooleanExpression bookIdEq(Long bookId) {
        return book.id.eq(bookId);
    }

    private Querydsl querydsl() {
        return Objects.requireNonNull(getQuerydsl());
    }
}

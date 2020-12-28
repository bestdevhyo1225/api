package com.hyoseok.dynamicdatasource.data;

import com.hyoseok.dynamicdatasource.domain.item.dto.BookSearchResult;
import com.hyoseok.dynamicdatasource.domain.item.entity.Book;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookQueryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

import static com.hyoseok.dynamicdatasource.domain.item.entity.QBook.*;

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
    public Page<BookSearchResult> findBooksByPagination(Pageable pageable, boolean useSearchBtn) {
        JPAQuery<BookSearchResult> query = queryFactory
                .select(
                        Projections.constructor(
                                BookSearchResult.class,
                                book.id,
                                book.title,
                                book.author,
                                book.price
                        )
                )
                .from(book)
                .orderBy(book.id.desc());

        JPQLQuery<BookSearchResult> pagination = querydsl().applyPagination(pageable, query);

        // 1. 검색 버튼을 사용한 경우
        if (useSearchBtn) {
            int fixedPageSize = 10;
            int fixedTotalCount = fixedPageSize * pageable.getPageSize();
            return new PageImpl<>(pagination.fetch(), pageable, fixedTotalCount);
        }

        // 2. 페이지 버튼을 사용한 경우
        long totalCount = pagination.fetchCount();

        // 데이터 건 수를 초과한 페이지 버튼 클릭시 보정
        Pageable pageRequest = exchangePageRequest(pageable, totalCount);

        return new PageImpl<>(querydsl().applyPagination(pageRequest, query).fetch(), pageRequest, totalCount);
    }

    private BooleanExpression bookIdEq(Long bookId) {
        return book.id.eq(bookId);
    }

    private Pageable exchangePageRequest(Pageable pageable, long totalCount) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        long requestCount = (long)pageNumber * pageSize;

        // DB에 있는 전체 데이터 수가 요청한 데이터의 수보다 큰 경우, 그냥 반환한다.
        if (totalCount > requestCount) return pageable;

        int requestPageNumber = (int)Math.ceil((double) totalCount / pageNumber);
        log.debug("requestPageNumber {}", requestPageNumber);

        return PageRequest.of(requestPageNumber, pageSize);
    }

    private Querydsl querydsl() {
        return Objects.requireNonNull(getQuerydsl());
    }
}

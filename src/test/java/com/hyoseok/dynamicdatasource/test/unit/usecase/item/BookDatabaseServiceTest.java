package com.hyoseok.dynamicdatasource.test.unit.usecase.item;

import com.hyoseok.dynamicdatasource.domain.item.*;
import com.hyoseok.dynamicdatasource.usecase.item.BookDatabaseService;
import com.hyoseok.dynamicdatasource.usecase.item.BookJpaService;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("BookDatabaseService 테스트")
@ExtendWith(MockitoExtension.class)
class BookDatabaseServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookQueryRepository bookQueryRepository;

    private BookDatabaseService bookDatabaseService;

    @BeforeEach
    void setUp() {
        bookDatabaseService = new BookJpaService(bookRepository, bookQueryRepository);
    }

    @Test
    void Book을_조회한다() {
        // given
        final Long bookId = 1L;
        final Book book = Book.builder()
                .title("title")
                .author("author")
                .price(25000)
                .build();

        given(bookRepository.findById(bookId)).willReturn(Optional.of(book));

        // when
        bookDatabaseService.findBook(bookId);

        // then
        then(bookRepository)
                .should()
                .findById(bookId);
    }

    @Test
    void Book의_상세정보를_조회한다() {
        // given
        final Long bookId = 1L;
        final BookDescription bookDescription = BookDescription.builder()
                .contents("contents")
                .build();
        final List<BookImage> bookImages = Collections.singletonList(
                BookImage.builder()
                        .kinds("kinds")
                        .imageUrl("imageUrl")
                        .sortOrder(0)
                        .build()
        );
        final Book book = Book.create("title", "author", 25000, bookDescription, bookImages);

        given(bookQueryRepository.findBookLeftJoin(bookId)).willReturn(Optional.of(book));

        // when
        bookDatabaseService.findBookDetail(bookId);

        // then
        then(bookQueryRepository)
                .should()
                .findBookLeftJoin(bookId);
    }

    @Test
    void SearchBtn방식의_Pagination을_통해_Book_리스트를_조회한다() {
        // given
        final boolean userSearchBtn = true;
        final int pageNumber = 0;
        final int pageSize = 10;
        final Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        final List<BookResult> books = Collections.singletonList(
                BookResult.builder()
                        .title("title")
                        .author("author")
                        .price(25000)
                        .build()
        );
        final long totalCount = 1L;
        final Page<BookResult> pagination = new PageImpl<>(books, pageRequest, totalCount);

        given(bookQueryRepository.findBooksByPagination(pageRequest, userSearchBtn))
                .willReturn(pagination);

        // when
        bookDatabaseService.findBooksByPagination(userSearchBtn, pageNumber, pageSize);

        // then
        then(bookQueryRepository)
                .should()
                .findBooksByPagination(pageRequest, userSearchBtn);
    }
}

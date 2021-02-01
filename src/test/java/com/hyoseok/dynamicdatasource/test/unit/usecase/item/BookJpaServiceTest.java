package com.hyoseok.dynamicdatasource.test.unit.usecase.item;

import com.hyoseok.dynamicdatasource.domain.item.*;
import com.hyoseok.dynamicdatasource.usecase.item.BookDatabaseService;
import com.hyoseok.dynamicdatasource.usecase.item.BookJpaService;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookDetailResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookPaginationResult;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("BookJpaService 테스트")
@ExtendWith(MockitoExtension.class)
class BookJpaServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookQueryRepository bookQueryRepository;

    private BookDatabaseService bookJpaService;

    @BeforeEach
    void setUp() {
        bookJpaService = new BookJpaService(bookRepository, bookQueryRepository);
    }

    @Test
    void findBook_메소드_내부에서_findById_메소드가_수행_되었는지_행위_및_상태를_검증한다() {
        // given
        final Long bookId = 1L;
        final Book book = Book.builder()
                .title("title")
                .author("author")
                .price(25000)
                .build();

        given(bookRepository.findById(bookId)).willReturn(Optional.of(book));

        // when
        final BookResult bookResult = bookJpaService.findBook(bookId);

        // then
        then(bookRepository).should().findById(bookId);

        assertThat(bookResult.getBookId()).isEqualTo(1L);
        assertThat(bookResult.getTitle()).isEqualTo("title");
        assertThat(bookResult.getAuthor()).isEqualTo("author");
        assertThat(bookResult.getPrice()).isEqualTo(25000);
    }

    @Test
    void findBookDetail_메소드_내부에서_findBookLeftJoin_메소드가_수행_되었는지_행위_및_상태를_검증한다() {
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
        final Book book = Book.create("title", "author", 25000, 10, bookDescription, bookImages);

        given(bookQueryRepository.findBookLeftJoin(bookId)).willReturn(Optional.of(book));

        // when
        final BookDetailResult bookDetailResult = bookJpaService.findBookDetail(bookId);

        // then
        then(bookQueryRepository).should().findBookLeftJoin(bookId);

        assertThat(bookDetailResult.getBookId()).isEqualTo(1L);
        assertThat(bookDetailResult.getTitle()).isEqualTo("title");
        assertThat(bookDetailResult.getAuthor()).isEqualTo("author");
        assertThat(bookDetailResult.getPrice()).isEqualTo(25000);
        assertThat(bookDetailResult.getStockQuantity()).isEqualTo(10);
        assertThat(bookDetailResult.getContents()).isEqualTo("contents");
        assertThat(bookDetailResult.getImages().get(0).getKinds()).isEqualTo("kinds");
        assertThat(bookDetailResult.getImages().get(0).getImageUrl()).isEqualTo("imageUrl");
        assertThat(bookDetailResult.getImages().get(0).getSortOrder()).isEqualTo(0);
    }

    @Test
    void findBooksByPagination_메소드_내부에서_findBooksByPagination_메소드가_수행_되었는지_행위_및_상태를_검증한다() {
        // given
        final boolean userSearchBtn = true;
        final int pageNumber = 0;
        final int pageSize = 10;
        final Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        final BookResult bookResult = BookResult.builder()
                .bookId(1L)
                .title("title")
                .author("author")
                .price(25000)
                .build();
        final List<BookResult> books = Collections.singletonList(bookResult);
        final long totalCount = 100L;
        final Page<BookResult> pagination = new PageImpl<>(books, pageRequest, totalCount);

        given(bookQueryRepository.findBooksByPagination(pageRequest, userSearchBtn))
                .willReturn(pagination);

        // when
        BookPaginationResult bookPaginationResult = bookJpaService.findBooksByPagination(userSearchBtn, pageNumber, pageSize);

        // then
        then(bookQueryRepository).should().findBooksByPagination(pageRequest, userSearchBtn);

        assertThat(bookPaginationResult.getBooks().get(0).getBookId()).isEqualTo(1L);
        assertThat(bookPaginationResult.getBooks().get(0).getTitle()).isEqualTo("title");
        assertThat(bookPaginationResult.getBooks().get(0).getAuthor()).isEqualTo("author");
        assertThat(bookPaginationResult.getBooks().get(0).getPrice()).isEqualTo(25000);
        assertThat(bookPaginationResult.getPageNumber()).isEqualTo(0);
        assertThat(bookPaginationResult.getPageSize()).isEqualTo(10);
        assertThat(bookPaginationResult.getTotalCount()).isEqualTo(100L);
    }
}

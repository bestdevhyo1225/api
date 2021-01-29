package com.hyoseok.dynamicdatasource.test.unit.usecase.item;

import com.hyoseok.dynamicdatasource.usecase.item.BookCacheService;
import com.hyoseok.dynamicdatasource.usecase.item.BookDatabaseService;
import com.hyoseok.dynamicdatasource.usecase.item.BookRedisService;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookDetailResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookImageSearchResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookPaginationResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("BookRedisService 테스트")
@ExtendWith(MockitoExtension.class)
class BookRedisServiceTest {

    @Mock
    private BookDatabaseService bookDatabaseService;

    private BookCacheService bookRedisService;

    @BeforeEach
    void setUp() {
        bookRedisService = new BookRedisService(bookDatabaseService);
    }

    @Test
    void findBook_메소드_내부에서_BookDatabaseService의_findBook_메소드가_호출_되었는지_행위_및_상태를_검증한다() {
        // given
        final Long bookId = 1L;
        final BookResult bookResult = BookResult.builder()
                .bookId(1L)
                .title("title")
                .author("author")
                .price(25000)
                .build();

        given(bookDatabaseService.findBook(bookId)).willReturn(bookResult);

        // when
        BookResult book = bookRedisService.findBook(bookId);

        // then
        then(bookDatabaseService).should().findBook(bookId);

        assertThat(book.getBookId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("title");
        assertThat(book.getAuthor()).isEqualTo("author");
        assertThat(book.getPrice()).isEqualTo(25000);
    }

    @Test
    void findBookDetail_메소드_내부에서_BookDatabaseService의_findBookDetail_메소드가_호출_되었는지_행위_및_상태를_검증한다() {
        // given
        final Long bookId = 1L;
        final BookImageSearchResult bookImageSearchResult = BookImageSearchResult.builder()
                .kinds("kinds")
                .imageUrl("imageUrl")
                .sortOrder(0)
                .build();
        final BookDetailResult bookDetailResult = BookDetailResult.builder()
                .bookId(1L)
                .title("title")
                .author("author")
                .price(25000)
                .contents("contents")
                .images(Collections.singletonList(bookImageSearchResult))
                .build();

        given(bookDatabaseService.findBookDetail(bookId)).willReturn(bookDetailResult);

        // when
        BookDetailResult bookDeatil = bookRedisService.findBookDetail(bookId);

        // then
        then(bookDatabaseService).should().findBookDetail(bookId);

        assertThat(bookDeatil.getBookId()).isEqualTo(1L);
        assertThat(bookDeatil.getTitle()).isEqualTo("title");
        assertThat(bookDeatil.getAuthor()).isEqualTo("author");
        assertThat(bookDeatil.getPrice()).isEqualTo(25000);
        assertThat(bookDeatil.getContents()).isEqualTo("contents");
        assertThat(bookDeatil.getImages().get(0).getKinds()).isEqualTo("kinds");
        assertThat(bookDeatil.getImages().get(0).getImageUrl()).isEqualTo("imageUrl");
        assertThat(bookDeatil.getImages().get(0).getSortOrder()).isEqualTo(0);
    }

    @Test
    void findBooksByPagination_메소드는_BookDatabaseService의_findBooksByPagination_메소드가_호출_되었는지_행위_및_상태를_검증한다() {
        // given
        final boolean userSearchBtn = false;
        final int pageNumber = 0;
        final int pageSize = 10;
        final int totalCount = 1;
        final BookResult bookResult = BookResult.builder()
                .bookId(1L)
                .title("title")
                .author("author")
                .price(25000)
                .build();
        final BookPaginationResult bookPaginationResult = BookPaginationResult.builder()
                .books(Collections.singletonList(bookResult))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalCount(totalCount)
                .build();

        given(bookDatabaseService.findBooksByPagination(userSearchBtn, pageNumber, pageSize))
                .willReturn(bookPaginationResult);

        // when
        BookPaginationResult bookPagination = bookRedisService.findBooksByPagination(userSearchBtn, pageNumber, pageSize);

        // then
        then(bookDatabaseService)
                .should()
                .findBooksByPagination(userSearchBtn, pageNumber, pageSize);

        assertThat(bookPagination.getBooks().get(0).getBookId()).isEqualTo(1L);
        assertThat(bookPagination.getBooks().get(0).getTitle()).isEqualTo("title");
        assertThat(bookPagination.getBooks().get(0).getAuthor()).isEqualTo("author");
        assertThat(bookPagination.getBooks().get(0).getPrice()).isEqualTo(25000);
        assertThat(bookPagination.getPageNumber()).isEqualTo(0);
        assertThat(bookPagination.getPageSize()).isEqualTo(10);
        assertThat(bookPagination.getTotalCount()).isEqualTo(1);
    }
}

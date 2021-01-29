package com.hyoseok.dynamicdatasource.test.unit.usecase.item;

import com.hyoseok.dynamicdatasource.usecase.item.BookCacheService;
import com.hyoseok.dynamicdatasource.usecase.item.BookDatabaseService;
import com.hyoseok.dynamicdatasource.usecase.item.BookRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("BookCacheService 테스트")
@ExtendWith(MockitoExtension.class)
public class BookCacheServiceTest {

    @Mock
    private BookDatabaseService bookDatabaseService;

    private BookCacheService bookCacheService;

    @BeforeEach
    void setUp() {
        bookCacheService = new BookRedisService(bookDatabaseService);
    }

    @Test
    void Book을_조회한다() {
        // given
        final Long bookId = 1L;

        // when

        // then
    }
}

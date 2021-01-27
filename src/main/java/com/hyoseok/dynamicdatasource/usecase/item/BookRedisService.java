package com.hyoseok.dynamicdatasource.usecase.item;

import com.hyoseok.dynamicdatasource.usecase.item.dto.BookDetailResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookPaginationResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookResult;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookRedisService implements BookCacheService {

    private final BookDatabaseService bookDatabaseService;

    @Override
    @Cacheable(cacheNames = "BookPagination")
    public BookPaginationResult findBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize) {
        return bookDatabaseService.findBooksByPagination(useSearchBtn, pageNumber, pageSize);
    }

    @Override
    @Cacheable(cacheNames = "BookDetail")
    public BookDetailResult findBookDetail(Long bookId) {
        return bookDatabaseService.findBookDetail(bookId);
    }

    @Override
    @Cacheable(cacheNames = "Book")
    public BookResult findBook(Long bookId) {
        return bookDatabaseService.findBook(bookId);
    }
}

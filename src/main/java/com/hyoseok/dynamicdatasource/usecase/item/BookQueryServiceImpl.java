package com.hyoseok.dynamicdatasource.usecase.item;

import com.hyoseok.dynamicdatasource.usecase.item.dto.BookDetailResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookPaginationResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookQueryServiceImpl implements BookQueryService {

    private final BookCacheService bookCacheService;
    private final BookDatabaseService bookDatabaseService;

    @Override
    @HystrixCommand(fallbackMethod = "fallbackBooksByPagination")
    public BookPaginationResult findBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize) {
        return bookCacheService.findBooksByPagination(useSearchBtn, pageNumber, pageSize);
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackBookDetail")
    public BookDetailResult findBookDetail(Long bookId) {
        return bookCacheService.findBookDetail(bookId);
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackBook")
    public BookResult findBook(Long bookId) {
        return bookCacheService.findBook(bookId);
    }

    private BookPaginationResult fallbackBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize) {
        return bookDatabaseService.findBooksByPagination(useSearchBtn, pageNumber, pageSize);
    }

    private BookDetailResult fallbackBookDetail(Long bookId) {
        return bookDatabaseService.findBookDetail(bookId);
    }

    private BookResult fallbackBook(Long bookId) {
        return bookDatabaseService.findBook(bookId);
    }
}

package com.hyoseok.dynamicdatasource.domain.usecase.item;

import com.hyoseok.dynamicdatasource.domain.dto.item.BookDetailResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookImageSearchResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookPaginationResult;
import com.hyoseok.dynamicdatasource.domain.entity.item.Book;
import com.hyoseok.dynamicdatasource.domain.entity.item.BookQueryRepository;
import com.hyoseok.dynamicdatasource.domain.entity.item.BookRepository;
import com.hyoseok.dynamicdatasource.domain.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookQueryServiceImpl implements BookQueryService {

    private final BookRepository bookRepository;
    private final BookQueryRepository bookQueryRepository;

    @Override
    @Cacheable(cacheNames = "BookPagination")
    public BookPaginationResult findBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize) {
        log.info("findBooksByPagination() called");

        Page<BookResult> pagination = bookQueryRepository.findBooksByPagination(PageRequest.of(pageNumber, pageSize), useSearchBtn);

        return BookPaginationResult.builder()
                .books(pagination.getContent())
                .pageNumber(pagination.getNumber())
                .pageSize(pagination.getSize())
                .totalCount(pagination.getTotalElements())
                .build();
    }

    @Override
    @Cacheable(cacheNames = "BookDetail")
    public BookDetailResult findBookLeftJoin(Long bookId) {
        log.info("findBookLeftJoin() called");

        Book book = bookQueryRepository.findBookLeftJoin(bookId).orElseThrow(NotFoundBookException::new);

        List<BookImageSearchResult> bookImageSearchResults = book.getBookImages().stream()
                .map(bookImage ->
                        BookImageSearchResult.builder()
                                .imageId(bookImage.getId())
                                .imageUrl(bookImage.getImageUrl())
                                .build())
                .collect(Collectors.toList());

        return BookDetailResult.builder()
                .bookId(bookId)
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .contents(book.getBookDescription().getContents())
                .images(bookImageSearchResults)
                .build();
    }

    @Override
    @Cacheable(cacheNames = "Book")
    public BookResult findBook(Long bookId) {
        log.info("findBook() called");

        Book book = bookRepository.findById(bookId).orElseThrow(NotFoundBookException::new);

        return BookResult.builder()
                .bookId(bookId)
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();
    }
}

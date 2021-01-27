package com.hyoseok.dynamicdatasource.usecase.item;

import com.hyoseok.dynamicdatasource.domain.item.Book;
import com.hyoseok.dynamicdatasource.domain.item.BookQueryRepository;
import com.hyoseok.dynamicdatasource.domain.item.BookRepository;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookDetailResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookImageSearchResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookPaginationResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookResult;
import com.hyoseok.dynamicdatasource.usecase.item.exception.NotFoundBookException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class BookJpaService implements BookDatabaseService {

    private final BookRepository bookRepository;
    private final BookQueryRepository bookQueryRepository;

    @Override
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
    public BookDetailResult findBookDetail(Long bookId) {
        log.info("findBookDetail() called");

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

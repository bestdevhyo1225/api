package com.hyoseok.dynamicdatasource.domain.usecase.item;

import com.hyoseok.dynamicdatasource.domain.dto.item.BookDetailSearchResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookSearchResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookImageSearchResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookPaginationResult;
import com.hyoseok.dynamicdatasource.domain.entity.item.Book;
import com.hyoseok.dynamicdatasource.domain.entity.item.BookQueryRepository;
import com.hyoseok.dynamicdatasource.domain.entity.item.BookRepository;
import com.hyoseok.dynamicdatasource.domain.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookQueryServiceImpl implements BookQueryService {

    private final BookRepository bookRepository;
    private final BookQueryRepository bookQueryRepository;

    @Override
    public BookPaginationResult findBooksByPagination(boolean useSearchBtn, int pageNumber, int pageSize) {
        Page<BookSearchResult> pagination = bookQueryRepository.findBooksByPagination(PageRequest.of(pageNumber, pageSize), useSearchBtn);

        return BookPaginationResult.builder()
                .books(pagination.getContent())
                .pageNumber(pagination.getNumber())
                .pageSize(pagination.getSize())
                .totalCount(pagination.getTotalElements())
                .build();
    }

    @Override
    public BookDetailSearchResult findBookLeftJoin(Long bookId) {
        Book book = bookQueryRepository.findBookLeftJoin(bookId).orElseThrow(NotFoundBookException::new);

        List<BookImageSearchResult> bookImageSearchResults = book.getBookImages().stream()
                .map(bookImage ->
                        BookImageSearchResult.builder()
                                .imageId(bookImage.getId())
                                .imageUrl(bookImage.getImageUrl())
                                .build())
                .collect(Collectors.toList());

        return BookDetailSearchResult.builder()
                .bookId(bookId)
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .contents(book.getBookDescription().getContents())
                .images(bookImageSearchResults)
                .build();
    }

    @Override
    public BookSearchResult findBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(NotFoundBookException::new);

        return BookSearchResult.builder()
                .bookId(bookId)
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();
    }
}

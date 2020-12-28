package com.hyoseok.dynamicdatasource.domain.item.usecase;

import com.hyoseok.dynamicdatasource.domain.item.dto.BookDetailSearchResult;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookSearchResult;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookImageSearchResult;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookPaginationResult;
import com.hyoseok.dynamicdatasource.domain.item.entity.Book;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookQueryRepository;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookRepository;
import com.hyoseok.dynamicdatasource.domain.exception.NotFoundBookException;
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

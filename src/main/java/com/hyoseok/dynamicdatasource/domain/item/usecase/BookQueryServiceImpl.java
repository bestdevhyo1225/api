package com.hyoseok.dynamicdatasource.domain.item.usecase;

import com.hyoseok.dynamicdatasource.domain.item.dto.FindBookDto;
import com.hyoseok.dynamicdatasource.domain.item.entity.Book;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookQueryServiceImpl implements BookQueryService {

    private final BookRepository bookRepository;

    @Override
    public FindBookDto findBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않습니다."));

        return FindBookDto.builder()
                .bookId(bookId)
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();
    }

    @Override
    public List<FindBookDto> findBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(book -> FindBookDto.builder()
                        .bookId(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .price(book.getPrice())
                        .build()
                )
                .collect(Collectors.toList());
    }
}

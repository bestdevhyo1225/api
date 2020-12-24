package com.hyoseok.dynamicdatasource.domain.item.usecase;

import com.hyoseok.dynamicdatasource.domain.item.dto.FindBookDetailDto;
import com.hyoseok.dynamicdatasource.domain.item.dto.FindBookDto;
import com.hyoseok.dynamicdatasource.domain.item.dto.FindBookImageDto;
import com.hyoseok.dynamicdatasource.domain.item.entity.Book;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookImage;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookQueryRepository;
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
    private final BookQueryRepository bookQueryRepository;

    @Override
    public FindBookDetailDto findBookLeftJoin(Long bookId) {
        Book book = bookQueryRepository.findBookLeftJoin(bookId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않습니다."));

        List<FindBookImageDto> bookImageDtos = book.getBookImages().stream()
                .map(bookImage ->
                        FindBookImageDto.builder()
                                .imageId(bookImage.getId())
                                .imageUrl(bookImage.getImageUrl())
                                .build())
                .collect(Collectors.toList());

        return FindBookDetailDto.builder()
                .bookId(bookId)
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .contents(book.getBookDescription().getContents())
                .images(bookImageDtos)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public FindBookDto findBook(Long bookId) {
        Book findBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않습니다."));

        return FindBookDto.builder()
                .bookId(bookId)
                .title(findBook.getTitle())
                .author(findBook.getAuthor())
                .price(findBook.getPrice())
                .build();
    }

    @Override
    public List<FindBookDto> findBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(book ->
                        FindBookDto.builder()
                                .bookId(book.getId())
                                .title(book.getTitle())
                                .author(book.getAuthor())
                                .price(book.getPrice())
                                .build())
                .collect(Collectors.toList());
    }
}

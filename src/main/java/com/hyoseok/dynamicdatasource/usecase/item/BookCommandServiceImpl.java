package com.hyoseok.dynamicdatasource.usecase.item;

import com.hyoseok.dynamicdatasource.domain.item.Book;
import com.hyoseok.dynamicdatasource.domain.item.BookDescription;
import com.hyoseok.dynamicdatasource.domain.item.BookImage;
import com.hyoseok.dynamicdatasource.domain.item.BookRepository;
import com.hyoseok.dynamicdatasource.usecase.item.dto.*;
import com.hyoseok.dynamicdatasource.usecase.item.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BookCommandServiceImpl implements BookCommandService {

    private final BookRepository bookRepository;

    @Override
    public BookCreatedResult create(BookCommand bookCommand,
                                    BookDescriptionCommand bookDescriptionCommand,
                                    List<BookImageCommand> bookImageCommands) {
        BookDescription bookDescription = BookDescription.builder()
                .contents(bookDescriptionCommand.getContents())
                .build();

        List<BookImage> bookImages = bookImageCommands.stream()
                .map(bookImageCommand ->
                        BookImage.builder()
                                .kinds(bookImageCommand.getKinds())
                                .imageUrl(bookImageCommand.getImageUrl())
                                .sortOrder(bookImageCommand.getSortOrder())
                                .build()
                )
                .collect(Collectors.toList());

        Book savedBook = bookRepository.save(
                Book.create(bookCommand.getTitle(), bookCommand.getAuthor(), bookCommand.getPrice(), bookDescription, bookImages)
        );

        return new BookCreatedResult(savedBook.getId());
    }

    @Override
    @CachePut(cacheNames = "Book", key = "#bookCommand.bookId")
    public BookResult update(BookCommand bookCommand) {
        log.info("updateBook() called");

        Book book = bookRepository.findById(bookCommand.getBookId()).orElseThrow(NotFoundBookException::new);

        book.change(bookCommand.getTitle(), bookCommand.getAuthor(), bookCommand.getPrice());

        return BookResult.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();
    }
}

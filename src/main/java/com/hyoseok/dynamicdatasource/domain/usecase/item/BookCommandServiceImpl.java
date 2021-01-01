package com.hyoseok.dynamicdatasource.domain.usecase.item;

import com.hyoseok.dynamicdatasource.domain.dto.item.BookCreationResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookModificationResult;
import com.hyoseok.dynamicdatasource.domain.entity.item.Book;
import com.hyoseok.dynamicdatasource.domain.entity.item.BookDescription;
import com.hyoseok.dynamicdatasource.domain.entity.item.BookImage;
import com.hyoseok.dynamicdatasource.domain.entity.item.BookRepository;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookDescriptionCommand;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookImageCommand;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookCommand;
import com.hyoseok.dynamicdatasource.domain.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCommandServiceImpl implements BookCommandService {

    private final BookRepository bookRepository;

    @Override
    public BookCreationResult create(BookCommand bookCommand,
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

        return new BookCreationResult(savedBook.getId());
    }

    @Override
    public BookModificationResult update(BookCommand bookCommand) {
        Book book = bookRepository.findById(bookCommand.getBookId()).orElseThrow(NotFoundBookException::new);

        book.change(bookCommand.getTitle(), bookCommand.getAuthor(), bookCommand.getPrice());

        return BookModificationResult.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();
    }
}

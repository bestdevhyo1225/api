package com.hyoseok.dynamicdatasource.domain.item.usecase;

import com.hyoseok.dynamicdatasource.domain.item.dto.BookCreationResult;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookModificationResult;
import com.hyoseok.dynamicdatasource.domain.item.entity.Book;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookDescription;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookImage;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookRepository;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookDescriptionCommand;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookImageCommand;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookCommand;
import com.hyoseok.dynamicdatasource.domain.item.usecase.exception.NotFoundBookException;
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

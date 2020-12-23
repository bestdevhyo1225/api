package com.hyoseok.dynamicdatasource.domain.item.usecase;

import com.hyoseok.dynamicdatasource.domain.item.dto.CreatedBookDto;
import com.hyoseok.dynamicdatasource.domain.item.dto.UpdatedBookDto;
import com.hyoseok.dynamicdatasource.domain.item.entity.Book;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookRepository;
import com.hyoseok.dynamicdatasource.domain.item.mapper.CreateBookMapper;
import com.hyoseok.dynamicdatasource.domain.item.mapper.UpdateBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCommandServiceImpl implements BookCommandService {

    private final BookRepository bookRepository;

    @Override
    public CreatedBookDto create(CreateBookMapper mapper) {
        Book book = Book.builder()
                .title(mapper.getTitle())
                .author(mapper.getAuthor())
                .price(mapper.getPrice())
                .build();

        bookRepository.save(book);

        return new CreatedBookDto(book.getId());
    }

    @Override
    public UpdatedBookDto update(UpdateBookMapper mapper) {
        Book book = bookRepository.findById(mapper.getBookId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않습니다."));

        book.change(mapper.getTitle(), mapper.getAuthor(), mapper.getPrice());

        return UpdatedBookDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();
    }
}

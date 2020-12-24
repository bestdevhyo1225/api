package com.hyoseok.dynamicdatasource.domain.item.usecase;

import com.hyoseok.dynamicdatasource.domain.item.dto.CreatedBookDto;
import com.hyoseok.dynamicdatasource.domain.item.dto.UpdatedBookDto;
import com.hyoseok.dynamicdatasource.domain.item.entity.Book;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookDescription;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookImage;
import com.hyoseok.dynamicdatasource.domain.item.entity.BookRepository;
import com.hyoseok.dynamicdatasource.domain.item.mapper.CreateBookDescriptionMapper;
import com.hyoseok.dynamicdatasource.domain.item.mapper.CreateBookImageMapper;
import com.hyoseok.dynamicdatasource.domain.item.mapper.CreateBookMapper;
import com.hyoseok.dynamicdatasource.domain.item.mapper.UpdateBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCommandServiceImpl implements BookCommandService {

    private final BookRepository bookRepository;

    @Override
    public CreatedBookDto create(CreateBookMapper bookMapper,
                                 CreateBookDescriptionMapper bookDescriptionMapper,
                                 List<CreateBookImageMapper> bookImageMappers) {
        BookDescription bookDescription = BookDescription.builder()
                .contents(bookDescriptionMapper.getContents())
                .build();

        List<BookImage> bookImages = bookImageMappers.stream()
                .map(bookImageMapper ->
                        BookImage.builder()
                                .kinds(bookImageMapper.getKinds())
                                .imageUrl(bookImageMapper.getImageUrl())
                                .sortOrder(bookImageMapper.getSortOrder())
                                .build()
                )
                .collect(Collectors.toList());

        Book savedBook = bookRepository.save(
                Book.create(bookMapper.getTitle(), bookMapper.getAuthor(), bookMapper.getPrice(), bookDescription, bookImages)
        );

        return new CreatedBookDto(savedBook.getId());
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

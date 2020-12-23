package com.hyoseok.dynamicdatasource.web;

import com.hyoseok.dynamicdatasource.domain.item.dto.CreatedBookDto;
import com.hyoseok.dynamicdatasource.domain.item.dto.FindBookDto;
import com.hyoseok.dynamicdatasource.domain.item.dto.UpdatedBookDto;
import com.hyoseok.dynamicdatasource.domain.item.mapper.CreateBookMapper;
import com.hyoseok.dynamicdatasource.domain.item.mapper.UpdateBookMapper;
import com.hyoseok.dynamicdatasource.domain.item.usecase.BookCommandService;
import com.hyoseok.dynamicdatasource.domain.item.usecase.BookQueryService;
import com.hyoseok.dynamicdatasource.web.request.CreateBookRequest;
import com.hyoseok.dynamicdatasource.web.request.UpdateBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookQueryService queryService;
    private final BookCommandService commandService;

    @GetMapping("/{id}")
    public ResponseEntity<FindBookDto> findBook(@PathVariable("id") Long bookId) {
        return ResponseEntity.ok().body(queryService.findBook(bookId));
    }

    @GetMapping
    public ResponseEntity<List<FindBookDto>> findBooks() {
        return ResponseEntity.ok().body(queryService.findBooks());
    }

    @PostMapping
    public ResponseEntity<CreatedBookDto> create(@RequestBody @Valid CreateBookRequest request) {
        CreateBookMapper mapper = CreateBookMapper.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .price(request.getPrice())
                .build();

        CreatedBookDto createdBookDto = commandService.create(mapper);

        return ResponseEntity.created(URI.create("/books/" + createdBookDto.getBookId())).body(createdBookDto);
    }

    @PatchMapping
    public ResponseEntity<UpdatedBookDto> update(@RequestBody @Valid UpdateBookRequest request) {
        UpdateBookMapper mapper = UpdateBookMapper.builder()
                .bookId(request.getBookId())
                .title(request.getTitle())
                .author(request.getAuthor())
                .price(request.getPrice())
                .build();

        UpdatedBookDto updatedBookDto = commandService.update(mapper);

        return ResponseEntity.ok().body(updatedBookDto);
    }
}

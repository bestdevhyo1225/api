package com.hyoseok.dynamicdatasource.web;

import com.hyoseok.dynamicdatasource.domain.item.dto.CreatedBookDto;
import com.hyoseok.dynamicdatasource.domain.item.dto.FindBookDetailDto;
import com.hyoseok.dynamicdatasource.domain.item.dto.FindBookDto;
import com.hyoseok.dynamicdatasource.domain.item.dto.UpdatedBookDto;
import com.hyoseok.dynamicdatasource.domain.item.mapper.CreateBookDescriptionMapper;
import com.hyoseok.dynamicdatasource.domain.item.mapper.CreateBookImageMapper;
import com.hyoseok.dynamicdatasource.domain.item.mapper.CreateBookMapper;
import com.hyoseok.dynamicdatasource.domain.item.mapper.UpdateBookMapper;
import com.hyoseok.dynamicdatasource.domain.item.usecase.BookCommandService;
import com.hyoseok.dynamicdatasource.domain.item.usecase.BookQueryService;
import com.hyoseok.dynamicdatasource.web.request.CreateBookImageRequest;
import com.hyoseok.dynamicdatasource.web.request.CreateBookRequest;
import com.hyoseok.dynamicdatasource.web.request.UpdateBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/detail/{id}")
    public ResponseEntity<FindBookDetailDto> findBookDetail(@PathVariable("id") Long bookId) {
        return ResponseEntity.ok().body(queryService.findBookLeftJoin(bookId));
    }

    @GetMapping
    public ResponseEntity<List<FindBookDto>> findBooks() {
        return ResponseEntity.ok().body(queryService.findBooks());
    }

    @PostMapping
    public ResponseEntity<CreatedBookDto> create(@RequestBody @Valid CreateBookRequest request) {
        CreateBookMapper bookMapper = CreateBookMapper.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .price(request.getPrice())
                .build();

        CreateBookDescriptionMapper bookDescriptionMapper = CreateBookDescriptionMapper.builder()
                .contents(request.getContents())
                .build();


        List<CreateBookImageMapper> bookImageMappers = request.getImages().stream()
                .map(createBookImageRequest ->
                        CreateBookImageMapper.builder()
                                .kinds(createBookImageRequest.getKinds())
                                .imageUrl(createBookImageRequest.getImageUrl())
                                .sortOrder(createBookImageRequest.getSortOrder())
                                .build()
                )
                .collect(Collectors.toList());

        CreatedBookDto createdBookDto = commandService.create(bookMapper, bookDescriptionMapper, bookImageMappers);

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

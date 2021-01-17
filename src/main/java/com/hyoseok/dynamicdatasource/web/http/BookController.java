package com.hyoseok.dynamicdatasource.web.http;

import com.hyoseok.dynamicdatasource.usecase.item.BookCommandService;
import com.hyoseok.dynamicdatasource.usecase.item.BookQueryService;
import com.hyoseok.dynamicdatasource.usecase.item.dto.*;
import com.hyoseok.dynamicdatasource.web.http.request.CreateBookRequest;
import com.hyoseok.dynamicdatasource.web.http.request.UpdateBookRequest;
import com.hyoseok.dynamicdatasource.web.http.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookQueryService queryService;
    private final BookCommandService commandService;

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> findBook(@PathVariable("id") Long bookId) {
        return ResponseEntity.ok().body(new SuccessResponse(queryService.findBook(bookId)));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<SuccessResponse> findBookDetail(@PathVariable("id") Long bookId) {
        return ResponseEntity.ok().body(new SuccessResponse(queryService.findBookLeftJoin(bookId)));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> findBooks(@RequestParam("useSearchBtn") boolean useSearchBtn,
                                                     @RequestParam("pageNumber") int pageNumber,
                                                     @RequestParam("pageSize") int pageSize) {
        return ResponseEntity.ok().body(
                new SuccessResponse(queryService.findBooksByPagination(useSearchBtn, pageNumber, pageSize))
        );
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> create(@RequestBody @Valid CreateBookRequest request) {
        BookCommand bookCommand = BookCommand.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .price(request.getPrice())
                .build();

        BookDescriptionCommand bookDescriptionCommand = BookDescriptionCommand.builder()
                .contents(request.getContents())
                .build();


        List<BookImageCommand> bookImageCommands = request.getImages().stream()
                .map(createBookImageRequest ->
                        BookImageCommand.builder()
                                .kinds(createBookImageRequest.getKinds())
                                .imageUrl(createBookImageRequest.getImageUrl())
                                .sortOrder(createBookImageRequest.getSortOrder())
                                .build()
                )
                .collect(Collectors.toList());

        BookCreatedResult bookCreatedResult = commandService.create(bookCommand, bookDescriptionCommand, bookImageCommands);

        return ResponseEntity.created(URI.create("/books/" + bookCreatedResult.getBookId()))
                .body(new SuccessResponse(bookCreatedResult));
    }

    @PatchMapping
    public ResponseEntity<SuccessResponse> update(@RequestBody @Valid UpdateBookRequest request) {
        BookCommand bookCommand = BookCommand.builder()
                .bookId(request.getBookId())
                .title(request.getTitle())
                .author(request.getAuthor())
                .price(request.getPrice())
                .build();

        BookResult bookResult = commandService.update(bookCommand);

        return ResponseEntity.ok().body(new SuccessResponse(bookResult));
    }
}

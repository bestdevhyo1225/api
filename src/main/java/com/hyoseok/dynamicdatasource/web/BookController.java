package com.hyoseok.dynamicdatasource.web;

import com.hyoseok.dynamicdatasource.domain.item.dto.BookCreationResult;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookModificationResult;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookDescriptionCommand;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookImageCommand;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookCommand;
import com.hyoseok.dynamicdatasource.domain.item.usecase.BookCommandService;
import com.hyoseok.dynamicdatasource.domain.item.usecase.BookQueryService;
import com.hyoseok.dynamicdatasource.web.request.CreateBookRequest;
import com.hyoseok.dynamicdatasource.web.request.UpdateBookRequest;
import com.hyoseok.dynamicdatasource.web.response.SuccessResponse;
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

        BookCreationResult bookCreationResult = commandService.create(bookCommand, bookDescriptionCommand, bookImageCommands);

        return ResponseEntity.created(URI.create("/books/" + bookCreationResult.getBookId()))
                .body(new SuccessResponse(bookCreationResult));
    }

    @PatchMapping
    public ResponseEntity<SuccessResponse> update(@RequestBody @Valid UpdateBookRequest request) {
        BookCommand bookCommand = BookCommand.builder()
                .bookId(request.getBookId())
                .title(request.getTitle())
                .author(request.getAuthor())
                .price(request.getPrice())
                .build();

        BookModificationResult bookModificationResult = commandService.update(bookCommand);

        return ResponseEntity.ok().body(new SuccessResponse(bookModificationResult));
    }
}

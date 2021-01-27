package com.hyoseok.dynamicdatasource.usecase.item;

import com.hyoseok.dynamicdatasource.domain.item.Book;
import com.hyoseok.dynamicdatasource.domain.item.BookDescription;
import com.hyoseok.dynamicdatasource.domain.item.BookImage;
import com.hyoseok.dynamicdatasource.domain.item.BookRepository;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookCommand;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookCreatedResult;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookDescriptionCommand;
import com.hyoseok.dynamicdatasource.usecase.item.dto.BookImageCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("BookCommandService 테스트")
@ExtendWith(MockitoExtension.class)
class BookCommandServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookCommandService bookCommandService;

    @BeforeEach
    void setUp() {
        bookCommandService = new BookCommandServiceImpl(bookRepository);
    }

    @Test
    void Book_등록에_성공한다() {
        // given
        given(bookRepository.save(any(Book.class))).will(AdditionalAnswers.returnsFirstArg());

        // when
        BookCommand bookCommand = BookCommand.builder()
                .title("title")
                .author("author")
                .price(25000)
                .build();

        BookDescriptionCommand bookDescriptionCommand = BookDescriptionCommand.builder()
                .contents("contents")
                .build();

        List<BookImageCommand> bookImageCommands = Collections.singletonList(
                BookImageCommand.builder()
                        .kinds("kinds")
                        .imageUrl("imageUrl")
                        .sortOrder(0)
                        .build()
        );

        bookCommandService.create(bookCommand, bookDescriptionCommand, bookImageCommands);

        // then
        then(bookRepository)
                .should()
                .save(any(Book.class));
    }
}

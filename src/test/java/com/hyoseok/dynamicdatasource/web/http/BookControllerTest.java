package com.hyoseok.dynamicdatasource.web.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyoseok.dynamicdatasource.usecase.item.BookCommandService;
import com.hyoseok.dynamicdatasource.usecase.item.BookQueryService;
import com.hyoseok.dynamicdatasource.usecase.item.dto.*;
import com.hyoseok.dynamicdatasource.web.http.request.CreateBookImageRequest;
import com.hyoseok.dynamicdatasource.web.http.request.CreateBookRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@DisplayName("BookController 테스트")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookCommandService bookCommandService;

    @MockBean
    private BookQueryService bookQueryService;

    @Test
    void Book을_조회한다() throws Exception {
        // given
        Long bookId = 1L;
        BookResult bookResult = BookResult.builder()
                .bookId(bookId)
                .title("JPA")
                .author("KimYH")
                .price(25000)
                .build();

        given(bookQueryService.findBook(bookId)).willReturn(bookResult);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/books/{id}", bookId).contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.bookId").value(bookResult.getBookId()))
                .andExpect(jsonPath("$.data.title").value(bookResult.getTitle()))
                .andExpect(jsonPath("$.data.author").value(bookResult.getAuthor()))
                .andExpect(jsonPath("$.data.price").value(bookResult.getPrice()));
    }

    @Test
    void Book_Detail을_조회한다() throws Exception {
        // given
        BookImageSearchResult bookImageSearchResult = BookImageSearchResult.builder()
                .imageId(1L)
                .imageUrl("imageUrl")
                .build();

        Long bookId = 1L;
        BookDetailResult bookDetailResult = BookDetailResult.builder()
                .bookId(bookId)
                .title("title1")
                .author("author1")
                .price(25000)
                .contents("contents")
                .images(Collections.singletonList(bookImageSearchResult))
                .build();

        given(bookQueryService.findBookLeftJoin(bookId)).willReturn(bookDetailResult);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/books/detail/{id}", bookId).contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.bookId").value(bookDetailResult.getBookId()))
                .andExpect(jsonPath("$.data.title").value(bookDetailResult.getTitle()))
                .andExpect(jsonPath("$.data.author").value(bookDetailResult.getAuthor()))
                .andExpect(jsonPath("$.data.price").value(bookDetailResult.getPrice()))
                .andExpect(jsonPath("$.data.contents").value(bookDetailResult.getContents()))
                .andExpect(jsonPath("$.data.images[0].imageId").value(bookDetailResult.getImages().get(0).getImageId()))
                .andExpect(jsonPath("$.data.images[0].imageUrl").value(bookDetailResult.getImages().get(0).getImageUrl()));
    }

    @Test
    void 검색_버튼을_눌렀다면_검색_방식으로_조회한다() throws Exception {
        // when
        boolean useSearchBtn = true;
        int pageNumber = 0;
        int pageSize = 10;

        Long bookId = 1L;
        BookResult bookResult = BookResult.builder()
                .bookId(bookId)
                .title("JPA")
                .author("KimYH")
                .price(25000)
                .build();

        BookPaginationResult bookPaginationResult = BookPaginationResult.builder()
                .books(Collections.singletonList(bookResult))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalCount(100)
                .build();

        given(bookQueryService.findBooksByPagination(useSearchBtn, pageNumber, pageSize))
                .willReturn(bookPaginationResult);

        // when
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("useSearchBtn", "true");
        params.add("pageNumber", "0");
        params.add("pageSize", "10");

        ResultActions resultActions = mockMvc.perform(
                get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params)
        ).andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.pageNumber").value(bookPaginationResult.getPageNumber()))
                .andExpect(jsonPath("$.data.pageSize").value(bookPaginationResult.getPageSize()))
                .andExpect(jsonPath("$.data.totalCount").value(bookPaginationResult.getTotalCount()));
    }

    @Test
    void 페이지_버튼을_눌렀다면_페이지_방식으로_조회한다() throws Exception {
        // when
        boolean useSearchBtn = false;
        int pageNumber = 0;
        int pageSize = 10;

        Long bookId = 1L;
        BookResult bookResult = BookResult.builder()
                .bookId(bookId)
                .title("JPA")
                .author("KimYH")
                .price(25000)
                .build();

        BookPaginationResult bookPaginationResult = BookPaginationResult.builder()
                .books(Collections.singletonList(bookResult))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalCount(1)
                .build();

        given(bookQueryService.findBooksByPagination(useSearchBtn, pageNumber, pageSize))
                .willReturn(bookPaginationResult);

        // when
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("useSearchBtn", "false");
        params.add("pageNumber", "0");
        params.add("pageSize", "10");

        ResultActions resultActions = mockMvc.perform(
                get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params)
        ).andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.pageNumber").value(bookPaginationResult.getPageNumber()))
                .andExpect(jsonPath("$.data.pageSize").value(bookPaginationResult.getPageSize()))
                .andExpect(jsonPath("$.data.totalCount").value(bookPaginationResult.getTotalCount()));
    }

    @Test
    void 토큰이_없으면_401_에러가_발생한다() throws Exception {
        // when
        List<CreateBookImageRequest> createBookImageRequests = Collections.singletonList(
                new CreateBookImageRequest("kinds", "imageUrl", 0)
        );

        CreateBookRequest createBookRequest = new CreateBookRequest(
                "JPA", "author KimYH", 25000, "Java Persistence Api", createBookImageRequests
        );

        ResultActions resultActions = mockMvc.perform(
                post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
        ).andDo(print());

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void 어드민_사용자가_아니면_403_에러가_발생한다() throws Exception {
        // when
        List<CreateBookImageRequest> createBookImageRequests = Collections.singletonList(
                new CreateBookImageRequest("kinds", "imageUrl", 0)
        );

        CreateBookRequest createBookRequest = new CreateBookRequest(
                "JPA", "author KimYH", 25000, "Java Persistence Api", createBookImageRequests
        );

        ResultActions resultActions = mockMvc.perform(
                post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
        ).andDo(print());

        // then
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void 어드민_사용자는_Book을_등록한다() throws Exception {
        // when
        List<CreateBookImageRequest> createBookImageRequests = Collections.singletonList(
                new CreateBookImageRequest("kinds", "imageUrl", 0)
        );

        CreateBookRequest createBookRequest = new CreateBookRequest(
                "JPA", "author KimYH", 25000, "Java Persistence Api", createBookImageRequests
        );

        ResultActions resultActions = mockMvc.perform(
                post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
        ).andDo(print());

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"));
    }
}

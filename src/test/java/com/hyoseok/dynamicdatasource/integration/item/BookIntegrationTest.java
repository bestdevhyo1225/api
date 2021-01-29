package com.hyoseok.dynamicdatasource.integration.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyoseok.dynamicdatasource.web.http.request.CreateBookImageRequest;
import com.hyoseok.dynamicdatasource.web.http.request.CreateBookRequest;
import com.hyoseok.dynamicdatasource.web.http.request.CreateJwtRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc // @SpringBootTest를 사용하는 경우, MockMvc를 사용하려면 @AutoConfigureMockMvc가 있어야 함.
@DisplayName("Book 관련 통합 테스트")
public class BookIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void Book을_등록한다() throws Exception {
        // given
        final List<CreateBookImageRequest> createBookImageRequests = Collections.singletonList(
                new CreateBookImageRequest("kinds", "imageUrl", 0)
        );
        final CreateBookRequest createBookRequest = new CreateBookRequest(
                "JPA", "author KimYH", 25000, "Java Persistence Api", createBookImageRequests
        );
        final CreateJwtRequest createJwtRequest = new CreateJwtRequest(1234, "ADMIN");
        final String content = mockMvc.perform(post("/jwt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createJwtRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final String token = objectMapper.readTree(content).get("data").toString();
        final String bearerToken = "Bearer " + token.substring(1, token.length() - 1);

        // when
        final ResultActions resultActions = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .content(objectMapper.writeValueAsString(createBookRequest)))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.bookId").value(1));
    }
}

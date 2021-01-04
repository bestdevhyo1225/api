package com.hyoseok.dynamicdatasource.web.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookRequest {
    @NotNull(message = "bookId는 반드시 존재해야 합니다.")
    private Long bookId;

    @NotBlank(message = "title은 비어 있을 수 없습니다.")
    @Length(max = 100, message = "title은 100자 이하로 작성해주세요")
    private String title;

    @NotBlank(message = "author는 비어 있을 수 없습니다.")
    @Length(max = 30, message = "author는 30자 이하로 작성해주세요")
    private String author;

    @Positive(message = "price는 0보다 큰 수를 입력해야 합니다.")
    private int price;
}

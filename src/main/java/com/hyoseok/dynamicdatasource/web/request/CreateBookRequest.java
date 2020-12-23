package com.hyoseok.dynamicdatasource.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreateBookRequest {
    @NotBlank(message = "title은 비어 있을 수 없습니다.")
    private String title;

    @NotBlank(message = "author는 비어 있을 수 없습니다.")
    private String author;

    @NotNull(message = "price는 반드시 존재해야 합니다.")
    @Min(value = 0, message = "0보다 큰 수를 입력하세요.")
    private int price;
}

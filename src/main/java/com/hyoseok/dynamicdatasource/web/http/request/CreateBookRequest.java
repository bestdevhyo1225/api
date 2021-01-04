package com.hyoseok.dynamicdatasource.web.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    @NotBlank(message = "title은 비어 있을 수 없습니다.")
    @Length(max = 100, message = "title은 100자 이하로 작성해주세요")
    private String title;

    @NotBlank(message = "author는 비어 있을 수 없습니다.")
    @Length(max = 30, message = "author는 30자 이하로 작성해주세요")
    private String author;

    @Positive(message = "price는 0보다 큰 수를 입력해야 합니다.")
    private int price;

    @NotBlank(message = "contents는 비어 있을 수 없습니다.")
    @Length(max = 30, message = "contents는 500자 이하로 작성해주세요")
    private String contents;

    @Valid
    private List<CreateBookImageRequest> images;
}

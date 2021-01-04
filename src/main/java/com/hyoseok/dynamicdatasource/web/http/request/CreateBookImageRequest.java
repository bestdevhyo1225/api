package com.hyoseok.dynamicdatasource.web.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookImageRequest {
    @NotBlank(message = "kinds는 비어 있을 수 없습니다.")
    @Length(max = 10, message = "kinds은 10자 이하로 작성해주세요")
    private String kinds;

    @NotBlank(message = "imageUrl은 비어 있을 수 없습니다.")
    @Length(max = 255, message = "imageUrl은 255자 이하로 작성해주세요")
    private String imageUrl;

    @PositiveOrZero(message = "sort0rder는 음수를 입력할 수 없습니다.")
    private int sortOrder;
}

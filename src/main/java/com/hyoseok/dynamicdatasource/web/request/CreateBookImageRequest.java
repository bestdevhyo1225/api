package com.hyoseok.dynamicdatasource.web.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreateBookImageRequest {
    @NotBlank(message = "kinds는 비어 있을 수 없습니다.")
    private String kinds;

    @NotBlank(message = "imageUrl는 비어 있을 수 없습니다.")
    private String imageUrl;

    @NotNull(message = "sortOrder는 반드시 존재해야 합니다.")
    private int sortOrder;
}

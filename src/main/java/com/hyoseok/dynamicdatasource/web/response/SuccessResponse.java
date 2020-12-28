package com.hyoseok.dynamicdatasource.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuccessResponse<T> {
    private final int status;
    private final T data;
}

package com.hyoseok.dynamicdatasource.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuccessResponse {
    private final int status;
    private final Object data;
}

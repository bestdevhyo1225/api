package com.hyoseok.dynamicdatasource.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuccessResponse {
    private final String status = "success";
    private final Object data;
}

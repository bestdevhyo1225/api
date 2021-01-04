package com.hyoseok.dynamicdatasource.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String status;
    private final String message;
}

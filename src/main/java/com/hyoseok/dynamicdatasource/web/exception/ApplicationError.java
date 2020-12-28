package com.hyoseok.dynamicdatasource.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationError {
    private final int status;
    private final String error;
    private final String message;
}

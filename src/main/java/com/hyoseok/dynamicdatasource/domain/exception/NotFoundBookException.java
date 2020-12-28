package com.hyoseok.dynamicdatasource.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundBookException extends RuntimeException {
    public NotFoundBookException() {
        super("Book이 존재하지 않습니다.");
    }
}

package com.hyoseok.dynamicdatasource.usecase.item.exception;

public class NotFoundBookException extends RuntimeException {
    public NotFoundBookException() {
        super("Book이 존재하지 않습니다.");
    }
}

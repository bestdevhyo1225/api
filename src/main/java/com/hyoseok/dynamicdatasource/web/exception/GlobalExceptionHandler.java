package com.hyoseok.dynamicdatasource.web.exception;

import com.hyoseok.dynamicdatasource.domain.item.usecase.exception.NotFoundBookException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { NotFoundBookException.class })
    public ResponseEntity<ApplicationError> handle(NotFoundBookException exception) {
        return new ResponseEntity<>(
                new ApplicationError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ApplicationError> handle(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(
                new ApplicationError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}

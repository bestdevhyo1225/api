package com.hyoseok.dynamicdatasource.web.exception;

import com.hyoseok.dynamicdatasource.usecase.item.exception.*;
import com.hyoseok.dynamicdatasource.web.http.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { NotFoundBookException.class })
    public ResponseEntity<ErrorResponse> handle(NotFoundBookException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), exception.getMessage()), HttpStatus.BAD_REQUEST
        );
    }
}

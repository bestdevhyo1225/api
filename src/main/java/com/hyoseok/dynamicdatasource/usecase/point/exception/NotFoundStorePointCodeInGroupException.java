package com.hyoseok.dynamicdatasource.usecase.point.exception;

public class NotFoundStorePointCodeInGroupException extends RuntimeException {
    public NotFoundStorePointCodeInGroupException() {
        super("StorePointCode가 Group에 존재하지 않습니다.");
    }
}

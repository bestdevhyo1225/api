package com.hyoseok.dynamicdatasource.domain.point.exception;

public class NotFoundStorePointCodeException  extends RuntimeException {
    public NotFoundStorePointCodeException() {
        super("존재하지 않는 StorePointCode 입니다.");
    }
}

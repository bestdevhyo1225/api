package com.hyoseok.dynamicdatasource.domain.item.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {
        super("재고 수량이 부족합니다.");
    }
}

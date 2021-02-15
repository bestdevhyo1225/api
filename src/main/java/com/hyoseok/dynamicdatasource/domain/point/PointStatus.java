package com.hyoseok.dynamicdatasource.domain.point;

public enum PointStatus {
    ACCUMULATE("적립"),
    USE("사용"),
    USE_CANCEL("사용 취소");

    private final String value;

    PointStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

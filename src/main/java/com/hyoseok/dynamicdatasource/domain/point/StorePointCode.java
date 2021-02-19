package com.hyoseok.dynamicdatasource.domain.point;

import com.hyoseok.dynamicdatasource.domain.point.exception.NotFoundStorePointCodeException;

import java.util.Arrays;

public enum StorePointCode {
    REWARD_A("REWARD_A", "A 보상"),
    REWARD_B("REWARD_B", "B 보상"),
    PROMOTION_A("PROMOTION_A", "A 프로모션을 통한 적립"),
    PROMOTION_B("PROMOTION_B", "B 프로모션을 통한 적립"),
    PROMOTION_C("PROMOTION_C", "C 프로모션을 통한 적립"),
    PROMOTION_D("PROMOTION_D", "D 프로모션을 통한 적립"),
    MILEAGE_CONVERSION_CANCEL("MILEAGE_CONVERSION_CANCEL", "마일리지 전환 취소"),
    MILEAGE_CONVERSION("MILEAGE_CONVERSION", "마일리지 전환"),
    EXTINCTION("EXTINCTION", "소멸");

    private final String code;
    private final String memo;

    StorePointCode(String code, String memo) {
        this.code = code;
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public static boolean isExistStorePointCode(String code) {
        return Arrays.stream(StorePointCode.values())
                .anyMatch(storePointCode -> storePointCode.code.equals(code));
    }

    public static StorePointCode convertStringtoStorePointCode(String code) {
        if (!isExistStorePointCode(code)) throw new NotFoundStorePointCodeException();
        return StorePointCode.valueOf(code);
    }

    public boolean isNotMileageConversionCancelCode() {
        return this != MILEAGE_CONVERSION_CANCEL;
    }
}

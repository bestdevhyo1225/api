package com.hyoseok.dynamicdatasource.domain.point;

import com.hyoseok.dynamicdatasource.domain.point.exception.NotFoundStorePointCodeException;

import java.util.Arrays;

public enum StorePointCode {
    REWARD_A("REWARD_A", "A 보상", 365L),
    REWARD_B("REWARD_B", "B 보상", 180L),
    PROMOTION_A("PROMOTION_A", "A 프로모션을 통한 적립", 7L),
    PROMOTION_B("PROMOTION_B", "B 프로모션을 통한 적립", 7L),
    PROMOTION_C("PROMOTION_C", "C 프로모션을 통한 적립", 1L),
    PROMOTION_D("PROMOTION_D", "D 프로모션을 통한 적립", 1L),
    MILEAGE_CONVERSION_CANCEL("MILEAGE_CONVERSION_CANCEL", "마일리지 전환 취소", 0L),
    MILEAGE_CONVERSION("MILEAGE_CONVERSION", "마일리지 전환", 0L),
    EXTINCTION("EXTINCTION", "소멸", 0L);

    private final String code;
    private final String memo;
    private final Long validDays;

    StorePointCode(final String code, final String memo, final Long validDays) {
        this.code = code;
        this.memo = memo;
        this.validDays = validDays;
    }

    public String getMemo() {
        return memo;
    }

    public Long getValidDays() { return validDays; }

    public static boolean isExistStorePointCode(String code) {
        return Arrays.stream(StorePointCode.values())
                .anyMatch(storePointCode -> storePointCode.code.equals(code));
    }

    public static StorePointCode convertStringtoStorePointCode(String code) {
        if (!isExistStorePointCode(code)) throw new NotFoundStorePointCodeException();
        return StorePointCode.valueOf(code);
    }

    public boolean isMileageConversionCancelCode() {
        return this == MILEAGE_CONVERSION_CANCEL;
    }
}

package com.hyoseok.dynamicdatasource.domain.point;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.hyoseok.dynamicdatasource.domain.point.StorePointCode.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public enum StorePointCodeGroup {
    ACCUMULATION(asList(REWARD_A, REWARD_B, PROMOTION_A, PROMOTION_B, PROMOTION_C, PROMOTION_D, MILEAGE_CONVERSION_CANCEL)),
    DEDUCT(asList(MILEAGE_CONVERSION, EXTINCTION)),
    EMPTY(emptyList());

    private final List<StorePointCode> storePointCodes;

    StorePointCodeGroup(List<StorePointCode> storePointCodes) {
        this.storePointCodes = storePointCodes;
    }

    public boolean hasStorePointCode(StorePointCode code) {
        return storePointCodes.stream()
                .anyMatch(storePointCode -> storePointCode == code);
    }

    public static StorePointCodeGroup getStorePointCodeGroup(StorePointCode code) {
        return Arrays.stream(StorePointCodeGroup.values())
                .filter(storePointCodeGroup -> storePointCodeGroup.hasStorePointCode(code))
                .findAny()
                .orElse(EMPTY);
    }

    public static boolean isAccumulationGroup(StorePointCode code) {
        return getStorePointCodeGroup(code) == ACCUMULATION;
    }

    public static boolean isDeductGroup(StorePointCode code) {
        return getStorePointCodeGroup(code) == DEDUCT;
    }
}

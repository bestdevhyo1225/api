package com.hyoseok.dynamicdatasource.test.unit.domain.point;

import com.hyoseok.dynamicdatasource.domain.point.StorePointCode;
import com.hyoseok.dynamicdatasource.domain.point.StorePointCodeGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("StorePointCodeGroup 테스트")
public class StorePointCodeGroupTest {

    @Test
    void hasStorePointCode_메소드는_존재하는_코드가_있으면_true를_반환한다() {
        // given
        StorePointCodeGroup storePointCodeGroup = StorePointCodeGroup.ACCUMULATION;

        // when
        boolean isExist = storePointCodeGroup.hasStorePointCode(StorePointCode.PROMOTION_A);

        // then
        assertThat(isExist).isTrue();
    }

    @Test
    void hasStorePointCode_메소드는_존재하는_코드가_없으면_false를_반환한다() {
        // given
        StorePointCodeGroup storePointCodeGroup = StorePointCodeGroup.ACCUMULATION;

        // when
        boolean isExist = storePointCodeGroup.hasStorePointCode(StorePointCode.EXTINCTION);

        // then
        assertThat(isExist).isFalse();
    }

    @Test
    void getStorePointCodeGroup_메소드는_StorePointCode가_속한_그룹을_반환한다() {
        // given
        StorePointCode storePointCode = StorePointCode.REWARD_A;

        // when
        StorePointCodeGroup storePointCodeGroup = StorePointCodeGroup.getStorePointCodeGroup(storePointCode);

        // then
        assertThat(storePointCodeGroup).isEqualTo(StorePointCodeGroup.ACCUMULATION);
    }

    @Test
    void isAccumulationGroup_메소드는_ACCUMULATION_그룹에_속한_StorePointCode_이면_true를_반환한다() {
        // given
        StorePointCode storePointCode = StorePointCode.PROMOTION_B;

        // when
        boolean isAccumulateGroup = StorePointCodeGroup.isAccumulationGroup(storePointCode);

        // then
        assertThat(isAccumulateGroup).isTrue();
    }

    @Test
    void isAccumulationGroup_메소드는_ACCUMULATION_그룹에_속하지_않는_StorePointCode_이면_false를_반환한다() {
        // given
        StorePointCode storePointCode = StorePointCode.EXTINCTION;

        // when
        boolean isAccumulateGroup = StorePointCodeGroup.isAccumulationGroup(storePointCode);

        // then
        assertThat(isAccumulateGroup).isFalse();
    }

    @Test
    void isDeductCode_메소드는_DEDUCT_그룹에_속한_StorePointCode_이면_true를_반환한다() {
        // given
        StorePointCode storePointCode = StorePointCode.MILEAGE_CONVERSION;

        // when
        boolean isAccumulateGroup = StorePointCodeGroup.isDeductGroup(storePointCode);

        // then
        assertThat(isAccumulateGroup).isTrue();
    }

    @Test
    void isDeductCode_메소드는_DEDUCT_그룹에_속하지_않는_StorePointCode_이면_false를_반환한다() {
        // given
        StorePointCode storePointCode = StorePointCode.REWARD_B;

        // when
        boolean isAccumulateGroup = StorePointCodeGroup.isDeductGroup(storePointCode);

        // then
        assertThat(isAccumulateGroup).isFalse();
    }
}

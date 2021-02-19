package com.hyoseok.dynamicdatasource.test.unit.domain.point;

import com.hyoseok.dynamicdatasource.domain.point.StorePointCode;
import com.hyoseok.dynamicdatasource.domain.point.exception.NotFoundStorePointCodeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("StorePointCode 테스트")
public class StorePointCodeTest {

    @Test
    void isExistStorePointCode_메소드는_존재하는_코드가_있으면_true를_반환한다() {
        // given
        String code = "PROMOTION_A";

        // when
        boolean isExist = StorePointCode.isExistStorePointCode(code);

        // then
        assertThat(isExist).isTrue();
    }

    @Test
    void isExistStorePointCode_메소드는_존재하는_코드가_없으면_false를_반환한다() {
        // given
        String code = "NOT_EXIST_CODE";

        // when
        boolean isExist = StorePointCode.isExistStorePointCode(code);

        // then
        assertThat(isExist).isFalse();
    }

    @Test
    void String_타입의_코드를_StorePointCode_타입으로_변환한다() {
        // given
        String code = "PROMOTION_A";

        // when
        StorePointCode storePointCode = StorePointCode.convertStringtoStorePointCode(code);

        // then
        assertThat(storePointCode).isEqualTo(StorePointCode.PROMOTION_A);
    }

    @Test
    void 존재하지_않는_코드를_변환하려고_하면_에러가_발생한다() {
        // given
        String code = "NOT_EXIST_CODE";

        // when
        NotFoundStorePointCodeException exception = assertThrows(
                NotFoundStorePointCodeException.class, () -> StorePointCode.convertStringtoStorePointCode(code)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 StorePointCode 입니다.");
    }
}

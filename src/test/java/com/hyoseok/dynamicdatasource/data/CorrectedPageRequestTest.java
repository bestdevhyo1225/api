package com.hyoseok.dynamicdatasource.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.*;

@DisplayName("CorrectedPageRequest 테스트")
class CorrectedPageRequestTest {

    @ParameterizedTest
    @CsvSource({
            "7, 47, 4",
            "8, 52, 5",
            "9, 71, 7",
            "9, 70, 6",
            "15, 120, 11",
            "27, 123, 12",
            "125, 1000, 99",
            "777, 1001, 100",
            "777, 1025, 102",
    })
    void 전체_데이터_수를_초과한_요청이면_가장_마지막_페이지번호를_반환한다(int pageNumber, int totalCount, int expectedPageNumber) {
        // given
        int pageSize = 10;
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);

        // when
        CorrectedPageRequest correctedPageRequest = new CorrectedPageRequest(pageRequest, totalCount);

        // then
        assertThat(correctedPageRequest.getPageNumber()).isEqualTo(expectedPageNumber);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 70, 5",
            "4, 77, 4",
            "7, 120, 7",
    })
    void 전체_데이터_수를_초과하지_않았으면_요청받은_페이지번호를_반환한다(int pageNumber, int totalCount, int expectedPageNumber) {
        // given
        int pageSize = 10;
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);

        // when
        CorrectedPageRequest correctedPageRequest = new CorrectedPageRequest(pageRequest, totalCount);

        // then
        assertThat(correctedPageRequest.getPageNumber()).isEqualTo(expectedPageNumber);
    }

}

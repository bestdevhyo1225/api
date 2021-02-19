package com.hyoseok.dynamicdatasource.test.unit.domain.point;

import com.hyoseok.dynamicdatasource.domain.point.Point;
import com.hyoseok.dynamicdatasource.domain.point.PointDetail;
import com.hyoseok.dynamicdatasource.domain.point.StorePointCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Point Aggregate 테스트")
public class PointAggregateTest {

    @Test
    void Point_Detail_엔티티를_생성한다() {
        // given
        final Long memberId = 1L;
        final StorePointCode code = StorePointCode.PROMOTION_A;
        final int tradingPoint = 20;
        final Long detailAccumulateId = 1L;
        final LocalDateTime expirationDate = LocalDateTime.now().plusWeeks(1L);

        // when
        PointDetail pointDetail = PointDetail.builder()
                .memberId(memberId)
                .code(code)
                .tradingPoint(tradingPoint)
                .detailAccumulateId(detailAccumulateId)
                .expirationDate(expirationDate)
                .build();

        // then
        assertThat(pointDetail.getMemberId()).isEqualTo(memberId);
        assertThat(pointDetail.getCode()).isEqualTo(code);
        assertThat(pointDetail.getTradingPoint()).isEqualTo(tradingPoint);
        assertThat(pointDetail.getDetailAccumulateId()).isEqualTo(detailAccumulateId);
        assertThat(pointDetail.getExpirationDate()).isEqualTo(expirationDate);
    }

    @Test
    void Point_Aggregate를_생성한다() {
        // given
        final Long memberId = 1L;
        final StorePointCode code = StorePointCode.PROMOTION_A;
        final int tradingPoint = 20;
        final Long detailAccumulateId = 1L;
        final LocalDateTime expirationDate = LocalDateTime.now().plusWeeks(1L);

        final List<PointDetail> pointDetails = Collections.singletonList(
                PointDetail.builder()
                        .memberId(memberId)
                        .code(code)
                        .tradingPoint(tradingPoint)
                        .detailAccumulateId(detailAccumulateId)
                        .expirationDate(expirationDate)
                        .build()
        );

        final Long orderId = 1L;

        // when
        Point point = Point.create(memberId, code, tradingPoint, orderId, expirationDate, pointDetails);

        // then
        assertThat(point.getMemberId()).isEqualTo(memberId);
        assertThat(point.getCode()).isEqualTo(code);
        assertThat(point.getAmounts()).isEqualTo(tradingPoint);
        assertThat(point.getOrderId()).isEqualTo(orderId);
        assertThat(point.getPointDetails().get(0).getMemberId()).isEqualTo(memberId);
        assertThat(point.getPointDetails().get(0).getCode()).isEqualTo(code);
        assertThat(point.getPointDetails().get(0).getTradingPoint()).isEqualTo(tradingPoint);
        assertThat(point.getPointDetails().get(0).getDetailAccumulateId()).isEqualTo(detailAccumulateId);
        assertThat(point.getPointDetails().get(0).getExpirationDate()).isEqualTo(expirationDate);
    }
}

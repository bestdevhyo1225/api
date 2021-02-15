package com.hyoseok.dynamicdatasource.test.unit.domain.point;

import com.hyoseok.dynamicdatasource.domain.point.Point;
import com.hyoseok.dynamicdatasource.domain.point.PointDetail;
import com.hyoseok.dynamicdatasource.domain.point.PointStatus;
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
        final PointStatus status = PointStatus.ACCUMULATE;
        final int tradingPoint = 20;
        final Long detailCancelId = 1L;
        final Long detailAccumulateId = 1L;
        final LocalDateTime expirationDate = LocalDateTime.now().plusWeeks(1L);

        // when
        PointDetail pointDetail = PointDetail.builder()
                .memberId(memberId)
                .status(status)
                .tradingPoint(tradingPoint)
                .detailCancelId(detailCancelId)
                .detailAccumulateId(detailAccumulateId)
                .expirationDate(expirationDate)
                .build();

        // then
        assertThat(pointDetail.getMemberId()).isEqualTo(memberId);
        assertThat(pointDetail.getStatus()).isEqualTo(status);
        assertThat(pointDetail.getStatus().getValue()).isEqualTo(status.getValue());
        assertThat(pointDetail.getTradingPoint()).isEqualTo(tradingPoint);
        assertThat(pointDetail.getDetailCancelId()).isEqualTo(detailCancelId);
        assertThat(pointDetail.getDetailAccumulateId()).isEqualTo(detailAccumulateId);
        assertThat(pointDetail.getExpirationDate()).isEqualTo(expirationDate);
    }

    @Test
    void Point_Aggregate를_생성한다() {
        // given
        final Long memberId = 1L;
        final PointStatus status = PointStatus.ACCUMULATE;
        final int tradingPoint = 20;
        final Long detailCancelId = 1L;
        final Long detailAccumulateId = 1L;
        final LocalDateTime expirationDate = LocalDateTime.now().plusWeeks(1L);

        final List<PointDetail> pointDetails = Collections.singletonList(
                PointDetail.builder()
                        .memberId(memberId)
                        .status(status)
                        .tradingPoint(tradingPoint)
                        .detailCancelId(detailCancelId)
                        .detailAccumulateId(detailAccumulateId)
                        .expirationDate(expirationDate)
                        .build()
        );

        final Long orderId = 1L;

        // when
        Point point = Point.create(memberId, status, tradingPoint, orderId, expirationDate, pointDetails);

        // then
        assertThat(point.getMemberId()).isEqualTo(memberId);
        assertThat(point.getStatus()).isEqualTo(status);
        assertThat(point.getStatus().getValue()).isEqualTo(status.getValue());
        assertThat(point.getAmounts()).isEqualTo(tradingPoint);
        assertThat(point.getOrderId()).isEqualTo(orderId);
        assertThat(point.getPointDetails().get(0).getMemberId()).isEqualTo(memberId);
        assertThat(point.getPointDetails().get(0).getStatus()).isEqualTo(status);
        assertThat(point.getPointDetails().get(0).getStatus().getValue()).isEqualTo(status.getValue());
        assertThat(point.getPointDetails().get(0).getTradingPoint()).isEqualTo(tradingPoint);
        assertThat(point.getPointDetails().get(0).getDetailCancelId()).isEqualTo(detailCancelId);
        assertThat(point.getPointDetails().get(0).getDetailAccumulateId()).isEqualTo(detailAccumulateId);
        assertThat(point.getPointDetails().get(0).getExpirationDate()).isEqualTo(expirationDate);
    }
}

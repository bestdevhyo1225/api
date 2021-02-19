package com.hyoseok.dynamicdatasource.test.unit.domain.point;

import com.hyoseok.dynamicdatasource.domain.point.Point;
import com.hyoseok.dynamicdatasource.domain.point.PointDetail;
import com.hyoseok.dynamicdatasource.domain.point.PointRepository;
import com.hyoseok.dynamicdatasource.domain.point.StorePointCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(readOnly = true)
@DisplayName("PointRepository 테스트")
public class PointRepositoryTest {

    @Autowired
    private PointRepository pointRepository;

    @Test
    @Transactional
    void MILEAGE_CONVERSION_CANCEL을_제외한_ACCUMULATION_그룹의_코드를_저장하면_pointDetailId_와_detailAccumulationId가_같다() {
        // given
        final Long memberId = 1L;
        final StorePointCode storePointCode = StorePointCode.REWARD_B;
        final int amounts = 1000;
        final Long orderId = 1L;

        final PointDetail pointDetail = PointDetail.builder()
                .memberId(memberId)
                .code(storePointCode)
                .tradingPoint(amounts)
                .build();

        final List<PointDetail> pointDetails = Collections.singletonList(pointDetail);

        final Point point = Point.create(memberId, storePointCode, amounts, orderId, pointDetails);

        // when
        pointRepository.save(point);

        // then
        assertThat(point.getMemberId()).isEqualTo(memberId);
        assertThat(point.getCode()).isEqualTo(storePointCode);
        assertThat(point.getAmounts()).isEqualTo(amounts);
        assertThat(point.getOrderId()).isEqualTo(orderId);
        assertThat(point.getPointDetails().get(0).getMemberId()).isEqualTo(memberId);
        assertThat(point.getPointDetails().get(0).getCode()).isEqualTo(storePointCode);
        assertThat(point.getPointDetails().get(0).getTradingPoint()).isEqualTo(amounts);
        assertThat(point.getPointDetails().get(0).getDetailAccumulateId()).isEqualTo(point.getPointDetails().get(0).getId());
    }
}

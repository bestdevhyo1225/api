package com.hyoseok.dynamicdatasource.domain.point;

import com.hyoseok.dynamicdatasource.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointDetail extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_detail_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StorePointCode code;

    @Column(nullable = false)
    private int tradingPoint;

    @Column(nullable = false)
    private Long detailAccumulateId;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    @Builder
    public PointDetail(final Long memberId, final StorePointCode code, final int tradingPoint,
                       final Long detailAccumulateId, final LocalDateTime expirationDate) {
        this.memberId = memberId;
        this.code = code;
        this.tradingPoint = tradingPoint;
        this.detailAccumulateId = detailAccumulateId == null ? 0 : detailAccumulateId;
        this.expirationDate = expirationDate;
    }

    @PostPersist
    public void changeDedailAccumulateId() {
        if (StorePointCodeGroup.isAccumulationGroup(this.code) && this.code.isNotMileageConversionCancelCode()) {
            this.detailAccumulateId = this.id;
        }
    }

    public void changePoint(Point point) {
        this.point = point;
    }
}

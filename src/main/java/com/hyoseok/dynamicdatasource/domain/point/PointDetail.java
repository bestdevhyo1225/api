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

    @Column(nullable = false)
    private PointStatus status;

    @Column(nullable = false)
    private int tradingPoint;

    @Column(nullable = false)
    private Long detailCancelId;

    @Column(nullable = false)
    private Long detailAccumulateId;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    @Builder
    public PointDetail(final Long memberId,
                       final PointStatus status,
                       final int tradingPoint,
                       final Long detailCancelId,
                       final Long detailAccumulateId,
                       final LocalDateTime expirationDate) {
        this.memberId = memberId;
        this.status = status;
        this.tradingPoint = tradingPoint;
        this.detailCancelId = detailCancelId;
        this.detailAccumulateId = detailAccumulateId;
        this.expirationDate = expirationDate;
    }

    public void changePoint(Point point) {
        this.point = point;
    }
}

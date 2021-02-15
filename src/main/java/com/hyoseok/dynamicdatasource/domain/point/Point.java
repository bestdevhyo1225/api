package com.hyoseok.dynamicdatasource.domain.point;

import com.hyoseok.dynamicdatasource.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private PointStatus status;

    @Column(nullable = false)
    private int amounts;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @OneToMany(mappedBy = "point", cascade = CascadeType.ALL)
    private final List<PointDetail> pointDetails = new ArrayList<>();

    public void addPointDetail(PointDetail pointDetail) {
        this.pointDetails.add(pointDetail);
        pointDetail.changePoint(this);
    }

    public static Point create(final Long memberId,
                               final PointStatus status,
                               final int amounts,
                               final Long orderId,
                               final LocalDateTime expirationDate,
                               final List<PointDetail> pointDetails) {
        Point point = new Point();

        point.memberId = memberId;
        point.status = status;
        point.amounts = amounts;
        point.orderId = orderId;
        point.expirationDate = expirationDate;

        pointDetails.forEach(point::addPointDetail);

        return point;
    }
}

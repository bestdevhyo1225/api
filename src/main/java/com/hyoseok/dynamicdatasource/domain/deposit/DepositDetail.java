package com.hyoseok.dynamicdatasource.domain.deposit;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepositDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_detail_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;

    @Column(nullable = false)
    private Long depositAccumulateDetailId;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private int point;

    @Column(nullable = false)
    private int nowPoint;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime addDatetime;

    private LocalDateTime expirationDatetime;
}

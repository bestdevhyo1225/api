package com.hyoseok.dynamicdatasource.domain.deposit;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "DEPOSIT_DETAIL_IX_TEST", columnList = "memberId, expiredAt, depositDetailAccId, amounts, type")
})
public class DepositDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long depositDetailAccId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private DepositType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DepositCode code;

    @Column(nullable = false)
    private int amounts;

    @Column(nullable = false)
    private int currentAmounts;

    @Column(columnDefinition = "datetime")
    private LocalDateTime expiredAt;

    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @Builder
    public DepositDetail(Long memberId, Long depositDetailAccId, DepositType type, DepositCode code,
                         int amounts, int currentAmounts, LocalDateTime expiredAt, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.depositDetailAccId = depositDetailAccId;
        this.type = type;
        this.code = code;
        this.amounts = amounts;
        this.currentAmounts = currentAmounts;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
    }

    public void changeDeposit(Deposit deposit) {
        this.deposit = deposit;
    }
}

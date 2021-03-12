package com.hyoseok.dynamicdatasource.domain.deposit;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private DepositType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DepositCode code;

    @Column(nullable = false)
    private int amounts;

    @Column(columnDefinition = "datetime")
    private LocalDateTime expiredAt;

    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "deposit", cascade = CascadeType.PERSIST)
    private final List<DepositDetail> depositDetails = new ArrayList<>();

    public void addDepositDetail(DepositDetail depositDetail) {
        depositDetails.add(depositDetail);
        depositDetail.changeDeposit(this);
    }

    @Builder
    public Deposit(Long memberId, DepositType type, DepositCode code, int amounts, LocalDateTime expiredAt, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.type = type;
        this.code = code;
        this.amounts = amounts;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
    }

    public static Deposit create(Long memberId, DepositType type, DepositCode code, int amounts, LocalDateTime expiredAt,
                                 LocalDateTime createdAt, List<DepositDetail> depositDetails) {
        Deposit deposit = Deposit.builder()
                .memberId(memberId)
                .type(type)
                .code(code)
                .amounts(amounts)
                .expiredAt(expiredAt)
                .createdAt(createdAt)
                .build();

        depositDetails.forEach(deposit::addDepositDetail);

        return deposit;
    }
}

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
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long mileageId;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private int point;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime addDatetime;

    private LocalDateTime expirationDatetime;
}

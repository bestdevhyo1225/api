package com.hyoseok.dynamicdatasource.domain.mileage;

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
public class Mileage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mileage_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long orderPaymentId;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private int point;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime addDatetime;

    private LocalDateTime expirationDatetime;
}

package com.hyoseok.dynamicdatasource.domain.ss;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class EntityA {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    public EntityA(Long memberId) {
        this.memberId = memberId;
    }
}

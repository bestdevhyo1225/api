package com.hyoseok.dynamicdatasource.domain.ss;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class EntityB {

    @Id
    @GeneratedValue
    private Long id;

//    @Column(name = "member_id")
//    private Long memberId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private EntityA entityA;

    public EntityB(EntityA entityA) {
        this.entityA = entityA;
    }
}

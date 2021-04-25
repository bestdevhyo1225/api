package com.hyoseok.dynamicdatasource.test.unit.domain.ss;

import com.hyoseok.dynamicdatasource.domain.ss.EntityA;
import com.hyoseok.dynamicdatasource.domain.ss.EntityARepository;
import com.hyoseok.dynamicdatasource.domain.ss.EntityB;
import com.hyoseok.dynamicdatasource.domain.ss.EntityBRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class EntityAandBRepositoryTest {

    @Autowired
    private EntityARepository entityARepository;

    @Autowired
    private EntityBRepository entityBRepository;

    @Test
    void saveTest() {
        final Long memberId = 1L;
        final EntityA entityA = new EntityA(memberId);
        final EntityB entityB = new EntityB(entityA);

        entityARepository.save(entityA);
        entityBRepository.save(entityB);
    }
}

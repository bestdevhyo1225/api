package com.hyoseok.dynamicdatasource.data;

import com.hyoseok.dynamicdatasource.domain.deposit.DepositQueryRepository;
import com.hyoseok.dynamicdatasource.domain.deposit.DepositType;
import com.hyoseok.dynamicdatasource.usecase.deposit.dto.FindAvailableDeposit2Dto;
import com.hyoseok.dynamicdatasource.usecase.deposit.dto.FindAvailableDepositDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hyoseok.dynamicdatasource.domain.deposit.QDepositDetail.depositDetail;

@Repository
@RequiredArgsConstructor
public class DepositQueryRepositoryImpl implements DepositQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public List<FindAvailableDeposit2Dto> findAvailableDepositAmountsWithNativeQuery(Long memberId, DepositType type, LocalDateTime datetime) {
        entityManager.clear();

        String query = "SELECT * " +
                        "FROM ( " +
                            "SELECT a.deposit_detail_acc_id, " +
                                    "a.remaining_amounts, " +
                                    "CASE " +
                                        "WHEN @acc + a.remaining_amounts <= @amount THEN 1 " +
                                        "WHEN @acc < @amount THEN 2 " +
                                        "ELSE 0 END AS target, " +
                                    "(@acc \\:= @acc + a.remaining_amounts) AS acc_point " +
                            "FROM ( " +
                                    "SELECT deposit_detail_acc_id, sum(amounts) AS remaining_amounts, expired_at " +
                                    "FROM deposit_detail " +
                                    "WHERE member_id = 1 " +
                                        "AND `type` = 'A_TYPE' " +
                                        "AND expired_at > now() " +
                                    "GROUP BY expired_at, deposit_detail_acc_id " +
                                    "HAVING sum(amounts) > 0 " +
                                    "ORDER BY expired_at, deposit_detail_acc_id" +
                                ") a, " +
                                "(SELECT @acc \\:= 0, @amount \\:= 506230) b) c " +
                        "WHERE c.target != 0";

        Query nativeQuery = entityManager.createNativeQuery(query);

        List<Object[]> resultList = nativeQuery.getResultList();

        for (Object[] row : resultList) {
            System.out.println("row[0] = " + row[0]);
            System.out.println("row[1] = " + row[1]);
            System.out.println("row[2] = " + row[2]);
            System.out.println("row[3] = " + row[3]);
        }

        return new ArrayList<>();
    }

    @Override
    public List<FindAvailableDepositDto> findAvailableDepositAmounts(Long memberId, DepositType type, LocalDateTime datetime) {
        return queryFactory
                .select(
                        Projections.constructor(
                                FindAvailableDepositDto.class,
                                depositDetail.depositDetailAccId,
                                depositDetail.amounts.sum(),
                                depositDetail.expiredAt
                        )
                )
                .from(depositDetail)
                .where(
                        depositDetailMemberIdEq(memberId),
                        depositDetailTypeEq(type),
                        depositDetailExpiredAtGt(datetime)
                )
                .groupBy(depositDetail.expiredAt, depositDetail.depositDetailAccId)
                .having(depositDetailSumAmountsGtZero())
                .orderBy(depositDetail.expiredAt.asc(), depositDetail.depositDetailAccId.asc())
                .fetch();
    }

    private BooleanExpression depositDetailMemberIdEq(Long memberId) {
        return depositDetail.memberId.eq(memberId);
    }

    private BooleanExpression depositDetailTypeEq(DepositType type) {
        return depositDetail.type.eq(type);
    }

    private BooleanExpression depositDetailExpiredAtGt(LocalDateTime datetime) {
        return depositDetail.expiredAt.gt(datetime);
    }

    private BooleanExpression depositDetailSumAmountsGtZero() {
        return depositDetail.amounts.sum().gt(0);
    }
}

package com.hyoseok.dynamicdatasource.domain.deposit;

import com.hyoseok.dynamicdatasource.usecase.deposit.dto.FindAvailableDeposit2Dto;
import com.hyoseok.dynamicdatasource.usecase.deposit.dto.FindAvailableDepositDto;

import java.time.LocalDateTime;
import java.util.List;

public interface DepositQueryRepository {
    List<FindAvailableDepositDto> findAvailableDepositAmounts(Long memberId, DepositType type, LocalDateTime datetime);
    List<FindAvailableDeposit2Dto> findAvailableDepositAmountsWithNativeQuery(
            Long memberId, DepositType type, LocalDateTime datetime
    );
}

package com.hyoseok.dynamicdatasource.usecase.deposit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAvailableDepositDto {
    Long depositDetailAccId;
    int remainingAmounts;
    LocalDateTime expiredAt;
}

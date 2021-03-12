package com.hyoseok.dynamicdatasource.usecase.deposit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAvailableDeposit2Dto {
    Long depositDetailAccId;
    int remainingAmounts;
    int accAmounts;
    int target;
}

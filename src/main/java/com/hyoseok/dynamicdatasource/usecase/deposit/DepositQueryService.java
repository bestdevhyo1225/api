package com.hyoseok.dynamicdatasource.usecase.deposit;

import com.hyoseok.dynamicdatasource.domain.deposit.DepositQueryRepository;
import com.hyoseok.dynamicdatasource.domain.deposit.DepositType;
import com.hyoseok.dynamicdatasource.usecase.deposit.dto.FindAvailableDeposit2Dto;
import com.hyoseok.dynamicdatasource.usecase.deposit.dto.FindAvailableDepositDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepositQueryService {

    private final DepositQueryRepository depositQueryRepository;

    public void findAvailableDepositAmounts(Long memberId, int deductAmounts) {
        long startTime = System.currentTimeMillis();

        List<FindAvailableDepositDto> availableAmounts =
                depositQueryRepository.findAvailableDepositAmounts(memberId, DepositType.A_TYPE, LocalDateTime.now());

//        List<FindAvailableDeposit2Dto> availableAmounts2 =
//                depositQueryRepository.findAvailableDepositAmountsWithNativeQuery(memberId, DepositType.A_TYPE, LocalDateTime.now());

        long endTime = System.currentTimeMillis();

        System.out.println("execution time = " + (endTime - startTime) + " ms");

        for (FindAvailableDepositDto availableDepositDto : availableAmounts) {
            System.out.println("----- deductAmounts = " + deductAmounts + " -----");
            Long depositDetailAccId = availableDepositDto.getDepositDetailAccId();
            int currentAmounts = availableDepositDto.getRemainingAmounts();
            if (currentAmounts >= deductAmounts) {
                System.out.println("currentAmounts = " + currentAmounts);
                System.out.println("depositDetailAccId = " + depositDetailAccId);
                break;
            }

            System.out.println("currentAmounts = " + currentAmounts);
            System.out.println("depositDetailAccId = " + depositDetailAccId);
            deductAmounts -= currentAmounts;
        }
    }
}

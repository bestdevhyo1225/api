package com.hyoseok.dynamicdatasource.web.http;

import com.hyoseok.dynamicdatasource.usecase.deposit.DepositQueryService;
import com.hyoseok.dynamicdatasource.web.http.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deposit")
public class DepositController {

    private final DepositQueryService depositQueryService;

    @GetMapping("/available-amounts/{memberId}")
    public ResponseEntity<SuccessResponse> findAvailableDepositAmounts(@PathVariable("memberId") Long memberId,
                                                                       @RequestParam("deductAmounts") int deductAmounts) {
        depositQueryService.findAvailableDepositAmounts(memberId, deductAmounts);
        return ResponseEntity.ok().body(null);
    }
}

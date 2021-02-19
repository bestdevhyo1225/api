package com.hyoseok.dynamicdatasource.usecase.point.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointAccumulateCommand {
    private Long memberId;
    private Long orderId;
    private String code;
    private int amounts;
}

package com.hyoseok.dynamicdatasource.usecase.point;

import com.hyoseok.dynamicdatasource.domain.point.*;
import com.hyoseok.dynamicdatasource.usecase.point.dto.PointAccumulateCommand;
import com.hyoseok.dynamicdatasource.usecase.point.dto.PointCreatedResult;
import com.hyoseok.dynamicdatasource.usecase.point.exception.NotFoundStorePointCodeInGroupException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Collections.singletonList;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PointCommandServiceImpl implements PointCommandService {

    private final PointRepository pointRepository;

    @Override
    public PointCreatedResult create(final PointAccumulateCommand command) {
        StorePointCode storePointCode = StorePointCode.convertStringtoStorePointCode(command.getCode());

        if (StorePointCodeGroup.isAccumulationGroup(storePointCode)) {
            PointDetail pointDetail = PointDetail.builder()
                    .memberId(command.getMemberId())
                    .code(storePointCode)
                    .tradingPoint(command.getAmounts())
                    .build();

            Point point = Point.create(
                    command.getMemberId(), storePointCode, command.getAmounts(), command.getOrderId(), singletonList(pointDetail)
            );

            pointRepository.save(point);

            return new PointCreatedResult(point.getId());
        }

        if (StorePointCodeGroup.isDeductGroup(storePointCode)) {
            return null;
        }

        throw new NotFoundStorePointCodeInGroupException();
    }


}


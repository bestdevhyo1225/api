package com.hyoseok.dynamicdatasource.usecase.point;

import com.hyoseok.dynamicdatasource.usecase.point.dto.PointAccumulateCommand;
import com.hyoseok.dynamicdatasource.usecase.point.dto.PointCreatedResult;

public interface PointCommandService {
    PointCreatedResult create(final PointAccumulateCommand pointAccumulateCommand);
}

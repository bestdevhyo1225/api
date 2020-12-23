package com.hyoseok.dynamicdatasource.domain.item.usecase;

import com.hyoseok.dynamicdatasource.domain.item.dto.CreatedBookDto;
import com.hyoseok.dynamicdatasource.domain.item.dto.UpdatedBookDto;
import com.hyoseok.dynamicdatasource.domain.item.mapper.CreateBookMapper;
import com.hyoseok.dynamicdatasource.domain.item.mapper.UpdateBookMapper;

public interface BookCommandService {
    CreatedBookDto create(CreateBookMapper mapper);
    UpdatedBookDto update(UpdateBookMapper mapper);
}

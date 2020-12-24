package com.hyoseok.dynamicdatasource.domain.item.usecase;

import com.hyoseok.dynamicdatasource.domain.item.dto.CreatedBookDto;
import com.hyoseok.dynamicdatasource.domain.item.dto.UpdatedBookDto;
import com.hyoseok.dynamicdatasource.domain.item.mapper.CreateBookDescriptionMapper;
import com.hyoseok.dynamicdatasource.domain.item.mapper.CreateBookImageMapper;
import com.hyoseok.dynamicdatasource.domain.item.mapper.CreateBookMapper;
import com.hyoseok.dynamicdatasource.domain.item.mapper.UpdateBookMapper;

import java.util.List;

public interface BookCommandService {
    CreatedBookDto create(CreateBookMapper bookMapper,
                          CreateBookDescriptionMapper bookDescriptionMapper,
                          List<CreateBookImageMapper> bookImageMappers);
    UpdatedBookDto update(UpdateBookMapper mapper);
}

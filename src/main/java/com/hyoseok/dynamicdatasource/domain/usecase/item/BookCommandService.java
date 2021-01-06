package com.hyoseok.dynamicdatasource.domain.usecase.item;

import com.hyoseok.dynamicdatasource.domain.dto.item.*;

import java.util.List;

public interface BookCommandService {
    BookCreatedResult create(BookCommand bookCommand,
                              BookDescriptionCommand bookDescriptionCommand,
                              List<BookImageCommand> bookImageCommands);
    BookResult update(BookCommand bookCommand);
}

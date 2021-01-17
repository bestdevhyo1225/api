package com.hyoseok.dynamicdatasource.usecase.item;

import com.hyoseok.dynamicdatasource.usecase.item.dto.*;

import java.util.List;

public interface BookCommandService {
    BookCreatedResult create(BookCommand bookCommand,
                             BookDescriptionCommand bookDescriptionCommand,
                             List<BookImageCommand> bookImageCommands);
    BookResult update(BookCommand bookCommand);
}

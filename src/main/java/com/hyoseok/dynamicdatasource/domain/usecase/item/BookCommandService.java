package com.hyoseok.dynamicdatasource.domain.usecase.item;

import com.hyoseok.dynamicdatasource.domain.dto.item.BookCreationResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookModificationResult;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookDescriptionCommand;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookImageCommand;
import com.hyoseok.dynamicdatasource.domain.dto.item.BookCommand;

import java.util.List;

public interface BookCommandService {
    BookCreationResult create(BookCommand bookCommand,
                              BookDescriptionCommand bookDescriptionCommand,
                              List<BookImageCommand> bookImageCommands);
    BookModificationResult update(BookCommand bookCommand);
}

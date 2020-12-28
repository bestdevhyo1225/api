package com.hyoseok.dynamicdatasource.domain.item.usecase;

import com.hyoseok.dynamicdatasource.domain.item.dto.BookCreationResult;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookModificationResult;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookDescriptionCommand;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookImageCommand;
import com.hyoseok.dynamicdatasource.domain.item.dto.BookCommand;

import java.util.List;

public interface BookCommandService {
    BookCreationResult create(BookCommand bookCommand,
                              BookDescriptionCommand bookDescriptionCommand,
                              List<BookImageCommand> bookImageCommands);
    BookModificationResult update(BookCommand bookCommand);
}

package com.hyoseok.dynamicdatasource.domain.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AggregateBook 테스트")
class AggregateBookTest {

    @Test
    void BookDescription_엔티티를_생성한다 () {
        // given
        String contents = "contents";

        // when
        BookDescription bookDescription = BookDescription.builder()
                .contents(contents)
                .build();

        // then
        assertThat(bookDescription.getContents()).isEqualTo(contents);
    }

    @Test
    void BookImage_엔티티를_생성한다 () {
        // given
        String kinds = "kinds";
        String imageUrl = "imageUrl";
        int sortOrder = 0;

        // when
        BookImage bookImage = BookImage.builder()
                .kinds(kinds)
                .imageUrl(imageUrl)
                .sortOrder(sortOrder)
                .build();

        // then
        assertThat(bookImage.getKinds()).isEqualTo(kinds);
        assertThat(bookImage.getImageUrl()).isEqualTo(imageUrl);
        assertThat(bookImage.getSortOrder()).isEqualTo(sortOrder);
    }

    @Test
    void Book_Aggregate를_생성한다 () {
        // given
        BookDescription bookDescription = BookDescription.builder()
                .contents("contents")
                .build();

        List<BookImage> bookImages = new ArrayList<>();
        bookImages.add(
                BookImage.builder()
                        .kinds("kinds")
                        .imageUrl("imageUrl")
                        .sortOrder(0)
                        .build()
        );

        String title = "JPA";
        String author = "저자1";
        int price = 20000;

        // when
        Book book = Book.create(title, author, price, bookDescription, bookImages);

        // then
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getAuthor()).isEqualTo(author);
        assertThat(book.getPrice()).isEqualTo(price);
        assertThat(book.getBookDescription().getContents()).isEqualTo(bookDescription.getContents());
        assertThat(book.getBookImages().get(0).getKinds()).isEqualTo(bookImages.get(0).getKinds());
        assertThat(book.getBookImages().get(0).getImageUrl()).isEqualTo(bookImages.get(0).getImageUrl());
        assertThat(book.getBookImages().get(0).getSortOrder()).isEqualTo(bookImages.get(0).getSortOrder());
    }
}

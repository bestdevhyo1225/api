package com.hyoseok.dynamicdatasource.domain.entity.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_image_id")
    private Long id;

    @Column(nullable = false, length = 10)
    private String kinds;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder
    public BookImage(String kinds, String imageUrl, int sortOrder) {
        this.kinds = kinds;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

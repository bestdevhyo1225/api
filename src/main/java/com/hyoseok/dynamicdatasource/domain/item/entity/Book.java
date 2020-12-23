package com.hyoseok.dynamicdatasource.domain.item.entity;

import com.hyoseok.dynamicdatasource.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 30)
    private String author;

    @Column(nullable = false)
    private int price;

    @Builder
    public Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public void change(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
}

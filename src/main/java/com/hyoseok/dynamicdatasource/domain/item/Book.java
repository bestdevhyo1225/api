package com.hyoseok.dynamicdatasource.domain.item;

import com.hyoseok.dynamicdatasource.domain.BaseEntity;
import com.hyoseok.dynamicdatasource.domain.item.exception.NotEnoughStockException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private int stockQuantity;

    // optional -> 연관이 선택 사항인지 여부를 의미함. false로 설정하면, Null이 아닌 관계가 항상 존재해야 한다.
    @OneToOne(mappedBy = "book", fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    private BookDescription bookDescription;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private final List<BookImage> bookImages = new ArrayList<>();

    @Builder
    public Book(String title, String author, int price, int stockQuantity) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void changeBookDescription(BookDescription bookDescription) {
        this.bookDescription = bookDescription;
        bookDescription.changeBook(this);
    }

    public void addBookImage(BookImage bookImage) {
        this.bookImages.add(bookImage);
        bookImage.changeBook(this);
    }

    public void decreaseStockQuantity(int stockQuantity) {
        if (this.stockQuantity - stockQuantity <= 0) throw new NotEnoughStockException();
        this.stockQuantity -= stockQuantity;
    }

    public void change(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public static Book create(String title, String author, int price, int stockQuantity,
                              BookDescription bookDescription, List<BookImage> bookImages) {
        Book book = new Book();

        book.title = title;
        book.author = author;
        book.price = price;
        book.stockQuantity = stockQuantity;

        book.changeBookDescription(bookDescription);
        bookImages.forEach(book::addBookImage);

        return book;
    }
}

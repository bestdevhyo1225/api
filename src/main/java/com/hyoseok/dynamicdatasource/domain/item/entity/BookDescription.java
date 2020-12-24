package com.hyoseok.dynamicdatasource.domain.item.entity;

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
public class BookDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_description_id")
    private Long id;

    @Column(nullable = false, length = 500)
    private String contents;

    @MapsId // 'BookDescription.id'와 매핑된다. 외래 키와 매핑한 연관관계를 기본 키에도 매핑하겠다는 뜻이다.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder
    public BookDescription(String contents) {
        this.contents = contents;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

package com.hyoseok.dynamicdatasource.domain.item;

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

    /*
    * Book 엔티티의 Id가 BookDescription 엔티티의 Id에 자동으로 매핑되기 때문에 @GeneratedValue를 사용할 필요가 없음
    * */
    @Id
    private Long bookId;

    @Column(nullable = false, length = 500)
    private String contents;

    /*
    * @MapsId
    * 외래 키와 매핑한 연관관계를 기본 키에도 매핑하겠다는 뜻이다.
    * 즉, BookDescription 엔티티의 Id와 Book 엔티티의 Id를 매핑시킨다는 의미!
    * */
    @MapsId
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

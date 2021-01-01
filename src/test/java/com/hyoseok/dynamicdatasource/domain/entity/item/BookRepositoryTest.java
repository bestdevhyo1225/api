package com.hyoseok.dynamicdatasource.domain.entity.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@DataJpaTest
@DisplayName("BookRepository 테스트")
class BookRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookRepository repository;

    @BeforeEach
    void init() {
        String title = "JPA";
        String author = "author1";
        int price = 25000;
        String contents = "JPA 책이고, 반드시 공부 해야합니다.";

        BookDescription bookDescription = BookDescription.builder().contents(contents).build();
        Book book = Book.create(title, author, price, bookDescription, new ArrayList<>());

        entityManager.persist(book);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void Book_Entity를_조회한다() {
        Long bookId = 1L;
        repository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않음"));
    }
}

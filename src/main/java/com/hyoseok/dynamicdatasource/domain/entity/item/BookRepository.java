package com.hyoseok.dynamicdatasource.domain.entity.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

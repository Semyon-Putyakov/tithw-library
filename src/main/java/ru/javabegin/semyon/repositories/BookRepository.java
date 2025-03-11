package ru.javabegin.semyon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.semyon.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByName(String name);
    List<Book> findByNameContainingIgnoreCase(String name);
    List<Book> findByNameContaining(String name);
}

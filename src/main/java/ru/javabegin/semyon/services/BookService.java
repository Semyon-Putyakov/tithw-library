package ru.javabegin.semyon.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javabegin.semyon.models.Book;
import ru.javabegin.semyon.models.Person;
import ru.javabegin.semyon.repositories.BookRepository;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@Component
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooksSorted(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page,size,Sort.by("name"))).getContent();
    }

    @Transactional(readOnly = true)
    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void saveBook(Book book){
        book.setName(book.getName().toLowerCase());
        bookRepository.save(book);
    }

    public void deleteBookById(int id) {
        bookRepository.deleteById(id);
    }

    public void updateBook(Book book, int id) {
        book.setBookId(id);
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public Book getBookByName(String name) {
        return bookRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksLikeName(String name) {
        return bookRepository.findByNameContainingIgnoreCase(name);
    }

    public void setBookOwner(Book book, Person person) {
        book.setOwner(person);
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public Person ownerIsPresent(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        return book.getOwner();
    }

    public void setBookOwner(int id, Person person){
        bookRepository.findById(id).orElse(null).setOwner(person);
    }

    public void deleteBookOwner(int id){
        bookRepository.findById(id).orElse(null).setOwner(null);
    }
}

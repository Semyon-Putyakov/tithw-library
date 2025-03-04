package ru.javabegin.semyon.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "book")
public class Book implements Comparable<Book> {
    @Override
    public int compareTo(Book o) {
        return this.name.compareTo(o.name);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "name")
    @NotEmpty(message = "Введите название!")
    @Size(max = 100, message = "Максимальное количество символов - 100!")
    private String name;

    @NotEmpty(message = "Введите автора!")
    @Size(max = 100, message = "Максимальное количество символов - 100!")
    @Column(name = "author")
    private String author;

    @Min(value = 1, message = "Введен некорректный год!")
    @Max(value = 2025, message = "Введен некорректный год!")
    @Column(name = "year")
    private int year;

    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    public Book(int bookId, String name, String author, int year, Person owner) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.year = year;
        this.owner = owner;
    }

    public Book(){

    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}

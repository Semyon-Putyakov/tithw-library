package ru.javabegin.semyon.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "person")
public class Person implements Comparable<Person> {
    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int personId;

    @NotEmpty(message = "Введите имя!")
    @Size(max = 100, message = "Максимальное количество символов - 100!")
    @Column(name = "name")
    private String name;

    @Min(value = 1900, message = "Некорректный год!")
    @Max(value = 2025, message = "Некорректный год!")
    @Column(name = "year")
    private int year;

    @OneToMany(mappedBy = "owner")
    private List<Book> bookList;

    public Person(int personId, String name, int year, List<Book> bookList) {
        this.personId = personId;
        this.name = name;
        this.year = year;
        this.bookList = bookList;
    }

    public Person(){

    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }


}

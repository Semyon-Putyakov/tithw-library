package ru.javabegin.semyon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.semyon.models.Person;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByName(String name);
    List<Person> findByNameContaining(String name);
}

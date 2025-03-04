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
import ru.javabegin.semyon.repositories.PersonRepository;

import java.util.List;

@Component
@Transactional
@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Person> getAllPersons(int page, int size) {
        return personRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<Person> getAllPersonsSorted(int page, int size) {
        return personRepository.findAll(PageRequest.of(page,size, Sort.by("name"))).getContent();
    }

    @Transactional(readOnly = true)
    public Person getPersonById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    public void savePerson(Person person) {
        personRepository.save(person);
    }

    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }

    public void updatePerson(Person person, int id) {
        person.setPersonId(id);
        personRepository.save(person);
    }

    @Transactional(readOnly = true)
    public Person getPersonByName(String name) {
        return personRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<Person> getPersonLikeName(String name) {
        return personRepository.findByNameContaining(name);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByPerson(int id) {
        Person person = personRepository.findById(id).orElse(null);
        Hibernate.initialize(person.getBookList());
        return person.getBookList();
    }

}

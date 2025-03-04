package ru.javabegin.semyon.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javabegin.semyon.models.Person;
import ru.javabegin.semyon.repositories.PersonRepository;

@Component
public class PersonValidator implements Validator {
    private final PersonRepository personRepository;

    @Autowired
    public PersonValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personRepository.findByName(person.getName()) != null){
            errors.rejectValue("name", "Такой человек уже существует!");
        }

    }
}

package ru.javabegin.semyon.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javabegin.semyon.models.Book;
import ru.javabegin.semyon.services.BookService;


@Component
public class BookValidator implements Validator {
    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if(bookService.getBookByName(book.getName()) != null) {
            errors.rejectValue("name", "Такая книга уже существует!");
        }
    }
}

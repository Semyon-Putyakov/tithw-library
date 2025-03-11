package ru.javabegin.semyon.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.semyon.models.Book;
import ru.javabegin.semyon.models.Person;
import ru.javabegin.semyon.services.BookService;
import ru.javabegin.semyon.services.PersonService;
import ru.javabegin.semyon.util.BookValidator;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookValidator bookValidator;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, BookValidator bookValidator, PersonService personService) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
        this.personService = personService;
    }

    @GetMapping
    public String getBooks(Model model,
                           @RequestParam(defaultValue = "false", name = "sortByName", required = false) boolean sortByName,
                           @RequestParam(defaultValue = "0", name = "page", required = false) int page,
                           @RequestParam(name = "search", required = false) String search)
    {
        if(page >= 0){
            List<Book> books;
            if(!sortByName){
                books = bookService.getAllBooks(page,10);
                if(!books.isEmpty()){
                    model.addAttribute("books", books);
                    model.addAttribute("page", page);
                } else {
                    return "redirect:/books?sortByName=" + sortByName + "&page=" + (page - 1);
                }
            }
            else{
                books = bookService.getAllBooksSorted(page,10);
                if(!books.isEmpty()){
                    model.addAttribute("books", books);
                    model.addAttribute("page", page);
                } else {
                    return "redirect:/books?sortByName=" + sortByName + "&page=" + (page - 1);
                }
            }
            model.addAttribute("sortByName", sortByName);
            model.addAttribute("listBooks", bookService.getBooksLikeName(search));
        } else {
            return "redirect:/books?sortByName=" + sortByName + "&page=" + 0;
        }
        return "book/BookList";
    }


    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("personWithBook", bookService.ownerIsPresent(id));
        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("people", personService.findAll());
        System.out.println(bookService.getBookById(id));

        return "book/BookPage";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") int id){
        bookService.deleteBookById(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBookGet(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookService.getBookById(id));
        return "book/BookEdit";
    }

    @PatchMapping("/{id}/edit")
    public String editBookPatch(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "book/BookEdit";
        }
        bookService.updateBook(book,id);
        return "redirect:/books";
    }

    @GetMapping("/create")
    public String createBookGet(Model model){
        model.addAttribute("book", new Book());
        return "book/BookCreate";
    }

    @PostMapping("/create")
    public String createBookPost(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()){
            return "book/BookCreate";
        }
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/setBook")
    public String setBook(@PathVariable("id") int id, @ModelAttribute("person") Person person, @ModelAttribute("book") Book book){
        System.out.println(book.getDate());
        bookService.setBookOwner(id, person, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}/setBook/delete")
    public String deleteBookOwner(@PathVariable("id") int id){
        bookService.deleteBookOwner(id);
        return "redirect:/books";
    }
}

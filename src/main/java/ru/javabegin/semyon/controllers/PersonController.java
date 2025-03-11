package ru.javabegin.semyon.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.semyon.models.Person;
import ru.javabegin.semyon.services.PersonService;
import ru.javabegin.semyon.util.PersonValidator;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String listPeople(Model model,
                             @RequestParam(name = "sortByName", required = false,defaultValue = "false") boolean sortByName,
                             @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                             @RequestParam(name = "search", required = false) String search){
        
        if(page >= 0){
            List<Person> people;
            if (sortByName) {
                people = personService.getAllPersonsSorted(page,10);
                if(!people.isEmpty()){
                    model.addAttribute("people", people);
                    model.addAttribute("page", page);
                } else {
                    return "redirect:/people?sortByName=" + sortByName + "&page=" + (page - 1);
                }
            } else {
                people = personService.getAllPersons(page,10);
                if(!people.isEmpty()){
                    model.addAttribute("people", people);
                    model.addAttribute("page", page);
                }else {
                    return "redirect:/people?sortByName=" + sortByName + "&page=" + (page - 1);
                }
            }
        } else {
            return "redirect:/people?sortByName=" + sortByName + "&page=" + 0;
        }

        model.addAttribute("sortByName", sortByName);

        List<Person> searchList = personService.getPersonLikeName(search);
        model.addAttribute("searchList", searchList);

        return "people/PersonList";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.getPersonById(id));
        model.addAttribute("books", personService.getBooksByPerson(id));
        return "people/PersonPage";
    }

    @GetMapping("/{id}/edit")
    public String editPersonGet(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.getPersonById(id));
        return "people/PersonEdit";
    }

    @PatchMapping("/{id}/edit")
    public String editPersonPatch(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/PersonEdit";
        }
        personService.updatePerson(person,id);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id){
        personService.deletePerson(id);
        return "redirect:/people";
    }

    @GetMapping("/create")
    public String createPersonGet(Model model){
        model.addAttribute("person", new Person());
        return "people/PersonCreate";
    }

    @PostMapping("/create")
    public String createPersonPost(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/PersonCreate";
        }
        personService.savePerson(person);
        return "redirect:/people";
    }

}

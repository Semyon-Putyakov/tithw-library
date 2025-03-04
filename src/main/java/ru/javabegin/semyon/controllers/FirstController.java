package ru.javabegin.semyon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping
    public String getWords(){
        return "firstPage/firstPage";
    }
}

package com.Company.controllers;


import com.Company.dao.BookDAO;
import com.Company.dao.PersonDAO;
import com.Company.models.Book;
import com.Company.models.Person;
import com.Company.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator){
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    // show all book
    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.getBooks());
        return "books/index";
    }

    // show book
    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") long id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.getBook(id));
        model.addAttribute("personWhoTake", bookDAO.isTaken(id));
        model.addAttribute("people", personDAO.getPeople());
        return "books/show";
    }

    // adding book
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "/books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors()){
            return "/books/new";
        }
        bookDAO.addBook(book);
        return "redirect:/books";
    }

    // updating book
    @GetMapping("/{id}/edit")
    public String changeBook(@PathVariable("id") long id, Model model){
        model.addAttribute("book", bookDAO.getBook(id));
        return "books/update";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") long id){
        book.setBookID(id);
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()){
            return "books/update";
        }
        bookDAO.updateBook(id, book);
        return "redirect:/books/{id}";
    }

    // deleting book
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") long id){
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    // free book
    @GetMapping("/{id}/free")
    public String freeBook(@PathVariable("id") long id){
        bookDAO.freeBook(id);
        return "redirect:/books/{id}";
    }

    // select person who take book
    @PatchMapping("/{id}/select-person")
    public String selectPerson(@PathVariable("id") long id, @ModelAttribute("person") Person person){
        bookDAO.selectPerson(id, person.getPersonId());
        return "redirect:/books/{id}";
    }
}

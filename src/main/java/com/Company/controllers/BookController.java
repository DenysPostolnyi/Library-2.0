package com.Company.controllers;

import com.Company.models.Book;
import com.Company.models.Person;
import com.Company.services.BooksService;
import com.Company.services.PeopleService;
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
    private final BooksService booksService;
    private final BookValidator bookValidator;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, BookValidator bookValidator, PeopleService peopleService){
        this.booksService = booksService;
        this.bookValidator = bookValidator;
        this.peopleService = peopleService;
    }

    // show all book
    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    // show book
    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") long id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("personWhoTake", booksService.findOne(id).getPerson());
        model.addAttribute("people", peopleService.findAll());
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
        booksService.save(book);
        return "redirect:/books";
    }

    // updating book
    @GetMapping("/{id}/edit")
    public String changeBook(@PathVariable("id") long id, Model model){
        model.addAttribute("book", booksService.findOne(id));
        return "books/update";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") long id){
        book.setBookID(id);
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()){
            return "books/update";
        }
        booksService.update(id, book);
        return "redirect:/books/{id}";
    }

    // deleting book
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") long id){
        booksService.delete(id);
        return "redirect:/books";
    }

    // free book
    @GetMapping("/{id}/free")
    public String freeBook(@PathVariable("id") long id){
        booksService.freeBook(id);
        return "redirect:/books/{id}";
    }

//    // select person who take book
    @PatchMapping("/{id}/select-person")
    public String selectPerson(@PathVariable("id") long id, @ModelAttribute("person") Person person){
        booksService.setPerson(id, person);
        return "redirect:/books/{id}";
    }
}

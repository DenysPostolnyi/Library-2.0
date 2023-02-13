package com.Company.controllers;

import com.Company.models.Person;
import com.Company.services.PeopleService;
import com.Company.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    // show all people
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    // show person
    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("takenBooks", peopleService.getBooksByPersonId(id));
        return "people/show";
    }

    // creating new person
    @GetMapping("/new")
    public String newPersson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String addPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    // updating person
    @GetMapping("/{id}/edit")
    public String changePerson(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/update";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        person.setId(id);
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/update";
        }
        peopleService.update(id, person);
        return "redirect:/people/{id}";
    }

    // deleting person
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") long id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}

package com.Company.controllers;

import com.Company.dao.PersonDAO;
import com.Company.models.Person;
import com.Company.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    // show all people
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.getPeople());
        return "people/index";
    }

    // show person
    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", personDAO.getPerson(id));
        model.addAttribute("takenBooks", personDAO.getTakenBooks(id));
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
        personDAO.addPerson(person);
        return "redirect:/people";
    }

    // updating person
    @GetMapping("/{id}/edit")
    public String changePerson(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", personDAO.getPerson(id));
        return "people/update";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        person.setPersonId(id);
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/update";
        }
        personDAO.updatePerson(id, person);
        return "redirect:/people/{id}";
    }

    // deleting person
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") long id) {
        personDAO.deletePerson(id);
        return "redirect:/people";
    }
}

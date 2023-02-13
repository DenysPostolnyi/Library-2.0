package com.Company.util;

import com.Company.models.Person;
import com.Company.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Controller
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    private PersonValidator(PeopleService peopleService){
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // check if person is present
        Optional<Person> personFromDB = peopleService.findByName(person.getFullName());
        if(personFromDB.isPresent() && personFromDB.get().getId() != person.getId()){
            errors.rejectValue("fullName", "", "This person is registered");
        }
    }
}

package com.Company.util;

import com.Company.dao.PersonDAO;
import com.Company.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Controller
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    private PersonValidator(PersonDAO personDAO){
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // check if person is present
        Optional<Person> personFromDB = personDAO.getPerson(person.getFullName());
        if(personFromDB.isPresent() && personFromDB.get().getPersonId() != person.getPersonId()){
            errors.rejectValue("fullName", "", "This person is registered");
        }
    }
}

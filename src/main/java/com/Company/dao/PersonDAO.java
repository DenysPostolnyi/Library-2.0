package com.Company.dao;

import com.Company.models.Book;
import com.Company.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // get all people
    public List<Person> getPeople(){
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    // add person to DB
    public void addPerson(Person person){
        jdbcTemplate.update("INSERT INTO Person (full_name, year_of_birth) VALUES (?, ?)", person.getFullName(), person.getYearOfBirth());
    }

    // get one person from DB
    public Person getPerson(long id){
        return jdbcTemplate.query("SELECT * From Person WHERE person_id=?", new Object[]{id}, new PersonMapper()).stream().findAny().orElse(null);
    }

    //update person
    public void updatePerson(long id, Person person){
        jdbcTemplate.update("UPDATE Person SET full_name=?, year_of_birth=? WHERE person_id = ?",person.getFullName(), person.getYearOfBirth(), id);
    }

    //delete person
    public void deletePerson(long id){
        if(getPerson(id) != null){
            jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
        }
    }

    // get books that person take
    public List<Book> getTakenBooks(long id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{id}, new BookMapper());
    }

    // check by full name if person is present
    public Optional<Person> getPerson(String fullName){
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?", new Object[]{fullName}, new PersonMapper()).stream().findAny();
    }
}

package com.Company.services;

import com.Company.models.Book;
import com.Company.models.Person;
import com.Company.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(long id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public Optional<Person> findByName(String name){
        return peopleRepository.findByFullName(name);
    }

    public List<Person> findByFullNameStart(String name) {
        return peopleRepository.findByFullNameStartsWith(name);
    }

    public List<Book> getBooksByPersonId(long id){
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(long id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson); // этот метод для удаления и для обновления
    }

    @Transactional
    public void delete(long id){
        peopleRepository.deleteById(id);
    }
}

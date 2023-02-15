package com.Company.repositories;

import com.Company.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByFullName(String fullName);
    List<Person> findByFullNameStartsWith(String fullName);
}

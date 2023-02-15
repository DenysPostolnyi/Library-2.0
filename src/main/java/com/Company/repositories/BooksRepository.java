package com.Company.repositories;

import com.Company.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    //    List<Book> findByPerson(Person person);
    Optional<Book> findByName(String name);

    List<Book> findByNameStartsWith(String starts);
}

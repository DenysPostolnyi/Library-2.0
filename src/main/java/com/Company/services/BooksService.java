package com.Company.services;

import com.Company.models.Book;
import com.Company.models.Person;
import com.Company.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(int page) {
        return booksRepository.findAll(PageRequest.of(page, 2)).getContent();
    }

    public int getPagesAmount() {
        return booksRepository.findAll().size() / 2;
    }

    public Book findOne(long id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

//    public List<Book> findByPerson(Person person) {
//        return booksRepository.findByPerson(person);
//    }

    public Optional<Book> findByName(String name) {
        return booksRepository.findByName(name);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(long id, Book updatedBook) {
        updatedBook.setBookID(id);
        booksRepository.save(updatedBook); // этот метод для удаления и для обновления
    }

    @Transactional
    public void delete(long id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void freeBook(long id) {
        Optional<Book> bookToFree = booksRepository.findById(id);
        bookToFree.ifPresent(value -> {
            value.setPerson(null);
        });
    }

    @Transactional
    public void setPerson(long id, Person person) {
        Optional<Book> bookToSet = booksRepository.findById(id);
        bookToSet.ifPresent(value -> {
            value.setPerson(person);
        });
    }
}

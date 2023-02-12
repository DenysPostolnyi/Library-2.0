package com.Company.dao;

import com.Company.models.Book;
import com.Company.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // get all books
    public List<Book> getBooks(){
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    // add book to DB
    public void addBook(Book book){
        jdbcTemplate.update("INSERT INTO Book (book_name, author_full_name, year_of_pub) VALUES (?, ?, ?)", book.getName(), book.getAuthorFullName(), book.getYearOfPub());
    }

    // get book
    public Book getBook(long id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{id}, new BookMapper()).stream().findAny().orElse(null);
    }

    // update
    public void updateBook(long id, Book book){
        jdbcTemplate.update("UPDATE Book SET book_name=?, author_full_name=?, year_of_pub=? WHERE book_id=?", book.getName(), book.getAuthorFullName(), book.getYearOfPub(), id);
    }

    //delete book
    public void deleteBook(long id){
        if(getBook(id) != null){
            jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
        }
    }

    // check if book is taken
    public Person isTaken(long id){
        return jdbcTemplate.query("SELECT p.person_id, p.full_name, p.year_of_birth FROM Book AS b INNER JOIN Person AS p on p.person_id = b.person_id WHERE b.book_id = ?", new Object[]{id}, new PersonMapper()).stream().findAny().orElse(null);
    }

    // free book
    public void freeBook(long id){
        jdbcTemplate.update("UPDATE Book SET person_id=null WHERE book_id=?", id);
    }

    // select person
    public void selectPerson(long id, long personId){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", personId, id);
    }

    // check if book with this name is present
    public Optional<Book> getBook(String name){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_name=?", new Object[]{name}, new BookMapper()).stream().findAny();
    }
}

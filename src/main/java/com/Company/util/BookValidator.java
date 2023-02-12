package com.Company.util;

import com.Company.dao.BookDAO;
import com.Company.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Controller
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        // if book is present
        Optional<Book> bookFromDB = bookDAO.getBook(book.getName());
        if (bookFromDB.isPresent() && bookFromDB.get().getBookID() != book.getBookID()) {
            errors.rejectValue("name", "", "Book with this name is already exists");
        }
    }
}

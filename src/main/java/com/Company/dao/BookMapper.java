package com.Company.dao;


import com.Company.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rst, int rowNum) throws SQLException {
        Book book = new Book();
        book.setBookID(rst.getLong("book_id"));
        book.setPersonId(rst.getLong("person_id"));
        book.setName(rst.getString("book_name"));
        book.setAuthorFullName(rst.getString("author_full_name"));
        book.setYearOfPub(rst.getInt("year_of_pub"));
        return book;
    }
}

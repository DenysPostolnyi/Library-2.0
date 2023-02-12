package com.Company.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {
    private long bookID;
    private long personId;

    @NotEmpty(message = "Field must be filed")
    @Size(min = 1, max = 200, message = "Book's name must be grater then 1 and lower then 200")
    private String name;

    // Name, Last Name, Surname - valid full name
    // John Kennedy
    @NotEmpty(message = "Field must be filed")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Author's full name should be in this format: Name, Last Name")
    private String authorFullName;

    @Min(value = 1700, message = "Year must be grater than 1700")
    private int yearOfPub;

    public Book() {}

    public Book(long bookID, long personId, String name, String authorFullName, int yearOfPub) {
        this.bookID = bookID;
        this.personId = personId;
        this.name = name;
        this.authorFullName = authorFullName;
        this.yearOfPub = yearOfPub;
    }

    public Book(String name, String authorFullName, int yearOfPub) {
        this.name = name;
        this.authorFullName = authorFullName;
        this.yearOfPub = yearOfPub;
    }

    public long getBookID() {
        return bookID;
    }

    public void setBookID(long bookID) {
        this.bookID = bookID;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public void setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
    }

    public int getYearOfPub() {
        return yearOfPub;
    }

    public void setYearOfPub(int yearOfPub) {
        this.yearOfPub = yearOfPub;
    }
}

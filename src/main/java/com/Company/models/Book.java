package com.Company.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookID;

    @NotEmpty(message = "Field must be filed")
    @Size(min = 1, max = 200, message = "Book's name must be grater then 1 and lower then 200")
    @Column(name = "book_name")
    private String name;

    // Name, Last Name, Surname - valid full name
    // John Kennedy
    @NotEmpty(message = "Field must be filed")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Author's full name should be in this format: Name, Last Name")
    @Column(name = "author_full_name")
    private String authorFullName;

    @Min(value = 1700, message = "Year must be grater than 1700")
    @Column(name = "year_of_pub")
    private int yearOfPub;

    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person person;

    public Book() {}

    public Book(long bookID, String name, String authorFullName, int yearOfPub) {
        this.bookID = bookID;
        this.name = name;
        this.authorFullName = authorFullName;
        this.yearOfPub = yearOfPub;
    }

    public Book(String name, String authorFullName, int yearOfPub) {
        this.name = name;
        this.authorFullName = authorFullName;
        this.yearOfPub = yearOfPub;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public long getBookID() {
        return bookID;
    }

    public void setBookID(long bookID) {
        this.bookID = bookID;
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

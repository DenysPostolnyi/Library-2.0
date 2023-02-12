package com.Company.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Person {
    private long personId;

    // Name, Last Name, Surname - valid full name
    // John Kennedy
    @NotEmpty(message = "Field must be filed")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Person's full name should be in this format: Name, Last Name")
    private String fullName;

    @Min(value = 1900, message = "Year must be grater than 1900")
    private int yearOfBirth;

    public Person() {}

    public Person(long personId, String fullName, int yearOfBirth) {
        this.personId = personId;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}

package com.Company.dao;

import com.Company.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rst, int rowNum) throws SQLException {
        Person person = new Person();
        person.setPersonId(rst.getLong("person_id"));
        person.setFullName(rst.getString("full_name"));
        person.setYearOfBirth(rst.getInt("year_of_birth"));
        return person;
    }
}

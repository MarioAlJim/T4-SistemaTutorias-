package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Person;

import java.sql.SQLException;

public interface IPersonDAO {
    public int insertPerson(Person person) throws SQLException;
}

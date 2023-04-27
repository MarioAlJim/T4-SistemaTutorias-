package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Person;

import java.sql.SQLException;

public interface IPersonDAO {
    int insertPerson(Person person) throws SQLException;

    int updatePerson(Person person) throws SQLException;
}

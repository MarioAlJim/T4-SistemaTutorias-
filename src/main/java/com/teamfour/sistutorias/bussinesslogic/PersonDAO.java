package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDAO implements IPersonDAO {
    @Override
    public int insertPerson(Person person) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        String query = "INSERT INTO person (name, paternal_surname, maternal_surname) VALUES (?, ?, ?)";
        PreparedStatement statement = dataBaseConnection.getConnection().prepareStatement(query);
        statement.setString(1, person.getName());
        statement.setString(2, person.getPaternalSurname());
        statement.setString(3, person.getMaternalSurname());
        int columns = statement.executeUpdate();
        int result;
        if (columns == 1) {
            query = "SELECT person_id FROM person WHERE name = ? AND paternal_surname = ? AND maternal_surname = ?";
            statement = dataBaseConnection.getConnection().prepareStatement(query);
            statement.setString(1, person.getName());
            statement.setString(2, person.getPaternalSurname());
            statement.setString(3, person.getMaternalSurname());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            result = resultSet.getInt("person_id");
        } else {
            result = -1;
        }
        dataBaseConnection.closeConection();
        return result;
    }

    @Override
    public int updatePerson(Person person) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        String query = "UPDATE person SET name = ?, paternal_surname = ?, maternal_surname = ? WHERE person_id = ?";
        PreparedStatement statement = dataBaseConnection.getConnection().prepareStatement(query);
        statement.setString(1, person.getName());
        statement.setString(2, person.getPaternalSurname());
        statement.setString(3, person.getMaternalSurname());
        statement.setInt(4, person.getIdPerson());
        int columns = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return columns;
    }
}

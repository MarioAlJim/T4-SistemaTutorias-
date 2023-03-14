package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.EducationProgram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EducationProgramDAO implements IEducationProgramDAO {
    @Override
    public ArrayList<EducationProgram> getEducationPrograms() throws SQLException {
        ArrayList<EducationProgram> educationPrograms = new ArrayList<>();
        String query = "SELECT name FROM education_program";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            educationPrograms.add(getEducationProgram(resultSet));
        }
        dataBaseConnection.closeConection();
        return educationPrograms;
    }

    @Override
    public boolean register(EducationProgram educationProgram) throws SQLException {
        boolean educationProgramWasRegistered = false;
        String query = "INSERT INTO education_program(name) VALUES(?)";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, educationProgram.getName());
        int executeUpdate = statement.executeUpdate();
        if (executeUpdate != 0){
            educationProgramWasRegistered = true;
        }
        dataBaseConnection.closeConection();
        return educationProgramWasRegistered;
    }

    private EducationProgram getEducationProgram(ResultSet resultSet) throws SQLException {
        EducationProgram educationProgram = new EducationProgram();
        String name = resultSet.getString("name");
        educationProgram.setName(name);
        return educationProgram;
    }
}

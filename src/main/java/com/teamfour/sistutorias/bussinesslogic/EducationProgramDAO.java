package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.EducationProgram;

import java.sql.*;
import java.util.ArrayList;

public class EducationProgramDAO implements IEducationProgramDAO {
    @Override
    public ArrayList<EducationProgram> getEducationPrograms() throws SQLException {
        ArrayList<EducationProgram> educationPrograms = new ArrayList<>();
        String query = "SELECT * FROM education_program";
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
    public int register(EducationProgram educationProgram) throws SQLException {
        int educationProgramWasRegistered;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO education_program(name) VALUES(?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, educationProgram.getName());
        if (statement.executeUpdate() != 0){
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            educationProgramWasRegistered = resultSet.getInt(1);
        } else {
            educationProgramWasRegistered = -1;
        }
        dataBaseConnection.closeConection();
        return educationProgramWasRegistered;
    }

    @Override
    public boolean updateEducationProgram(EducationProgram educationProgram) throws SQLException {
        boolean educationProgramModified = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "UPDATE education_program " +
                "SET name = ? " +
                "WHERE education_program_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, educationProgram.getName());
        statement.setInt(2, educationProgram.getIdEducationProgram());
        if(statement.executeUpdate() != 0)
            educationProgramModified = true;
        dataBaseConnection.closeConection();
        return educationProgramModified;
    }

    private EducationProgram getEducationProgram(ResultSet resultSet) throws SQLException {
        EducationProgram educationProgram = new EducationProgram();
        int educationProgramId = resultSet.getInt("education_program_id");
        String name = resultSet.getString("name");
        educationProgram.setIdEducationProgram(educationProgramId);
        educationProgram.setName(name);
        return educationProgram;
    }
}

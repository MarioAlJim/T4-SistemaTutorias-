package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.EducativeProgram;

import java.sql.*;
import java.util.ArrayList;

public class EducativeProgramDAO implements IEducativeProgramDAO {
    @Override
    public ArrayList<EducativeProgram> getEducativePrograms() throws SQLException {
        ArrayList<EducativeProgram> educativePrograms = new ArrayList<>();
        String query = "SELECT * FROM education_program";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            educativePrograms.add(getEducationProgram(resultSet));
        }
        dataBaseConnection.closeConection();
        return educativePrograms;
    }

    @Override
    public int register(EducativeProgram educativeProgram) throws SQLException {
        int educationProgramWasRegistered;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO education_program(name) VALUES(?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, educativeProgram.getName());
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
    public boolean updateEducativeProgram(EducativeProgram educativeProgram) throws SQLException {
        boolean educationProgramModified = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "UPDATE education_program " +
                "SET name = ? " +
                "WHERE education_program_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, educativeProgram.getName());
        statement.setInt(2, educativeProgram.getIdEducativeProgram());
        if(statement.executeUpdate() != 0)
            educationProgramModified = true;
        dataBaseConnection.closeConection();
        return educationProgramModified;
    }

    private EducativeProgram getEducationProgram(ResultSet resultSet) throws SQLException {
        EducativeProgram educativeProgram = new EducativeProgram();
        int educationProgramId = resultSet.getInt("education_program_id");
        String name = resultSet.getString("name");
        educativeProgram.setIdEducativeProgram(educationProgramId);
        educativeProgram.setName(name);
        return educativeProgram;
    }
}

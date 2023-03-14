package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.AcademicProblem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcademicProblemDAO implements IAcademicProblemDAO{

    @Override
    public int register(AcademicProblem academicProblem) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        String description = academicProblem.getDescription();
        String title = academicProblem.getTitle();
        int group = academicProblem.getGroup();
        int numberTutorados = academicProblem.getNumberTutorados();
        int register = academicProblem.getRegister();
        String query = "INSERT INTO academic_problems (title, description, numbertutorados, nrc, register_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setInt(3, numberTutorados);
            statement.setInt(4, group);
        statement.setInt(5 , register);
            insertedFiles = statement.executeUpdate();
        return insertedFiles;
    }

    @Override
    public int update(AcademicProblem academicProblem) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        String description = academicProblem.getDescription();
        String title = academicProblem.getTitle();
        int group = academicProblem.getGroup();
        int numberTutorados = academicProblem.getNumberTutorados();
        int register = academicProblem.getRegister();
        int id = academicProblem.getIdAcademicProblem();
        String query = "UPDATE academic_problems SET title = ?, description = ?, numbertutorados = ?, nrc = ?, register_id = ? WHERE academic_problems_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, description);
        statement.setInt(3, numberTutorados);
        statement.setInt(4, group);
        statement.setInt(5 , register);
        statement.setInt(6, id);
        insertedFiles = statement.executeUpdate();
        return insertedFiles;
    }

    @Override
    public int delete(AcademicProblem academicProblem) throws SQLException {
        return 0;
    }
}

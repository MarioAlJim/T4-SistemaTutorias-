package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.AcademicProblem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    @Override
    public ArrayList<AcademicProblem> getAcademicProblemsFromRegister(int registerId) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        ArrayList<AcademicProblem> academicProblems = new ArrayList<>();
        String query = "SELECT * FROM academic_problems WHERE register_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, registerId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            AcademicProblem academicProblem = new AcademicProblem();
            academicProblem.setIdAcademicProblem(resultSet.getInt("academic_problems_id"));
            academicProblem.setTitle(resultSet.getString("title"));
            academicProblem.setDescription(resultSet.getString("description"));
            academicProblem.setNumberTutorados(resultSet.getInt("numbertutorados"));
            academicProblem.setGroup(resultSet.getInt("nrc"));
            academicProblem.setRegister(resultSet.getInt("register_id"));
            boolean alreadyRegistered = false;
            for (AcademicProblem academicProblem1 : academicProblems) {
                if (academicProblem1.getIdAcademicProblem() == academicProblem.getIdAcademicProblem()) {
                    alreadyRegistered = true;
                    break;
                }
            }
            if (!alreadyRegistered) {
                academicProblems.add(academicProblem);
            }
        }
        return academicProblems;
    }
}

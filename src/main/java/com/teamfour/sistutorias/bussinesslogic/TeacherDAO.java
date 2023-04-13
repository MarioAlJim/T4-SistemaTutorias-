package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherDAO implements ITeacherDAO {
    @Override
    public ArrayList<Teacher> getTeachersByProgram(int idProgram) throws SQLException {
        ArrayList<Teacher> teachers = new ArrayList<>();
        String query = "SELECT DISTINCT t.personal_number, p.name, p.paternal_surname, p.maternal_surname FROM group_program gp " +
                "INNER JOIN teacher t ON t.personal_number = gp.personal_number " +
                "INNER JOIN person p ON P.person_id = T.person_id " +
                "WHERE gp.program_id = ?";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProgram);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            teachers.add(getTeacher(resultSet));
        }
        dataBaseConnection.closeConection();
        return teachers;
    }

    @Override
    public ArrayList<Teacher> getAllTeachers() throws SQLException {
        ArrayList<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM sistematutorias.teacher t " +
                        "INNER JOIN person p ON p.person_id = t.person_id;";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            do {
                teachers.add(getTeacher(resultSet));
            } while (resultSet.next());
        }

        dataBaseConnection.closeConection();
        return teachers;
    }

    private Teacher getTeacher(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        int person_id = resultSet.getInt("person_id");
        teacher.setIdPerson(person_id);
        int personalNumber = resultSet.getInt("personal_number");
        teacher.setPersonalNumber(personalNumber);
        String name = resultSet.getString("name");
        teacher.setName(name);
        String paternalSurname = resultSet.getString("paternal_surname");
        teacher.setPaternalSurname(paternalSurname);
        String maternalSurname = resultSet.getString("maternal_surname");
        teacher.setMaternalSurname(maternalSurname);
        return teacher;
    }

    @Override
    public int registerTeacher(Teacher teacher) throws SQLException {
        int result = 0;
        String queryPerson = "INSERT INTO person (name, paternal_surname, maternal_surname) VALUES (?,?,?);";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statementPerson = connection.prepareStatement(queryPerson);
        statementPerson.setString(1, teacher.getName());
        statementPerson.setString(2, teacher.getPaternalSurname());
        statementPerson.setString(3, teacher.getMaternalSurname());
        result = statementPerson.executeUpdate();

        if (result != 0) {
            String queryGetPerson = "SELECT person.person_id FROM person WHERE name = ? AND paternal_surname = ? AND maternal_surname = ?";
            PreparedStatement getPerson = connection.prepareStatement(queryGetPerson);
            getPerson.setString(1, teacher.getName());
            getPerson.setString(2, teacher.getPaternalSurname());
            getPerson.setString(3, teacher.getMaternalSurname());
            ResultSet resultSet = getPerson.executeQuery();
            if (resultSet.next()) {
                int person_id;
                do {
                    person_id = resultSet.getInt("person_id");
                }while (resultSet.next());
                String queryNewTeacher = "INSERT teacher (personal_number, person_id) VALUES (?, ?);";
                PreparedStatement statementNewTeacher = connection.prepareStatement(queryNewTeacher);
                statementNewTeacher.setInt(1, teacher.getPersonalNumber());
                statementNewTeacher.setInt(2, person_id);
                result = statementNewTeacher.executeUpdate();
            }
        }
        return result;
    }
}

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
        String query = "SELECT DISTINCT p.person_id, t.personal_number, p.name, p.paternal_surname, p.maternal_surname, gp.period_id " +
                "FROM group_program gp " +
                "INNER JOIN teacher t ON t.personal_number = gp.personal_number " +
                "INNER JOIN person p ON P.person_id = T.person_id " +
                "WHERE gp.program_id = ? AND t.active = 1";
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
                        "INNER JOIN person p ON p.person_id = t.person_id " +
                        "WHERE active = 1;";
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
        int result = -1;
        if (duplicatedTeacher(teacher.getPersonalNumber())) {
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
            dataBaseConnection.closeConection();
        }
        return result;
    }

    @Override
    public int modifyTeacher(Teacher updateTeacher, int oldPersonalNumber) throws SQLException {
        int result = 0;
        if ((oldPersonalNumber == updateTeacher.getPersonalNumber()) || (duplicatedTeacher(updateTeacher.getPersonalNumber()))) {
            String queryPerson = "UPDATE person SET name = ?, paternal_surname = ?, maternal_surname= ? WHERE (person_id = ?);";
            String queryTeacher = "UPDATE teacher SET personal_number = ? WHERE (personal_number = ?);";
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection connection = dataBaseConnection.getConnection();
            PreparedStatement statementPerson = connection.prepareStatement(queryPerson);
            statementPerson.setString(1, updateTeacher.getName());
            statementPerson.setString(2, updateTeacher.getPaternalSurname());
            statementPerson.setString(3, updateTeacher.getMaternalSurname());
            statementPerson.setInt(4, updateTeacher.getIdPerson());
            PreparedStatement statementTeacher = connection.prepareStatement(queryTeacher);
            statementTeacher.setInt(1, updateTeacher.getPersonalNumber());
            statementTeacher.setInt(2, oldPersonalNumber);
            result += statementPerson.executeUpdate();
            result += statementTeacher.executeUpdate();
            dataBaseConnection.closeConection();
        }
        return result;
    }

    public int deleteTeacher(int personalNumber) throws SQLException {
        int result = 0;
        String queryDeleteTeacher = "UPDATE teacher SET active = 0 WHERE (personal_number = ?);";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statementTeacher = connection.prepareStatement(queryDeleteTeacher);
        statementTeacher.setInt(1, personalNumber);
        result += statementTeacher.executeUpdate();
        return result;
    }

    public boolean duplicatedTeacher(int personal_number) throws SQLException {
        boolean duplicated = false;
        int result = 0;
        String query = "SELECT * FROM sistematutorias.teacher T WHERE T.personal_number = ?;";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, personal_number);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            result++;
        }
        if (result > 0) {
            duplicated = true;
        }
        dataBaseConnection.closeConection();
        return !duplicated;
    }
}

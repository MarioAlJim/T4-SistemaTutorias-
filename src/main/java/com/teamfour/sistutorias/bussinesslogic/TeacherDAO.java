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
    public ArrayList<Teacher> getTeachers() throws SQLException {
        ArrayList<Teacher> teachers = new ArrayList<>();
        String query = "SELECT T.personal_number, P.name, P.paternal_surname, P.maternal_surname FROM teacher T " +
                "INNER JOIN person P ON P.person_id = T.person_id";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            teachers.add(getTeacher(resultSet));
        }
        dataBaseConnection.closeConection();
        return teachers;
    }

    private Teacher getTeacher(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
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
}

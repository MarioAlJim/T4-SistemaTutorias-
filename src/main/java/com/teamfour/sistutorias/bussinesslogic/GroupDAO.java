package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.EE;
import com.teamfour.sistutorias.domain.EducationProgram;
import com.teamfour.sistutorias.domain.Group;
import com.teamfour.sistutorias.domain.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupDAO implements IGroupDAO {

    @Override
    public ArrayList<Group> groupsList(int idProgram) throws SQLException {
        ArrayList<Group> groups = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = ("SELECT gp.nrc, ee.name as experience, concat(p.name, ' ', p.paternal_surname, ' ', p.maternal_surname) as teacher, " +
                "gp.personal_number, gp.ee_id, gp.program_id " +
                "FROM sistematutorias.group_program gp " +
                "INNER JOIN ee ON ee.ee_id = gp.ee_id " +
                "INNER JOIN teacher t ON t.personal_number = gp.personal_number " +
                "INNER JOIN person p ON p.person_id = t.person_id " +
                "WHERE gp.program_id = ? ;");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProgram);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            do {
                EE ee = new EE();
                ee.setIdEe(resultSet.getInt("ee_id"));
                Teacher teacher = new Teacher();
                teacher.setPersonalNumber(resultSet.getInt("personal_number"));
                EducationProgram educationProgram = new EducationProgram();
                educationProgram.setIdEducationProgram(resultSet.getInt("program_id"));
                String experience = resultSet.getString("experience");
                String teacherName = resultSet.getString("teacher");
                int nrc = resultSet.getInt("nrc");

                Group group = new Group();
                group.setEducationProgram(educationProgram);
                group.setEe(ee);
                group.setTeacher(teacher);
                group.setExperience(experience);
                group.setTeacherName(teacherName);
                group.setNrc(nrc);
                groups.add(group);
            } while (resultSet.next());
        }
        dataBaseConnection.closeConection();
        return groups;
    }

    @Override
    public int registerGroup(Group newGroup) throws SQLException {
        int result = 0;
        if (checkAvailability(newGroup.getNrc())) {
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection connection = dataBaseConnection.getConnection();
            String query = "INSERT INTO group_program (nrc, ee_id, personal_number, program_id) VALUES (?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, newGroup.getNrc());
            statement.setInt(2, newGroup.getEe().getIdEe());
            statement.setInt(3, newGroup.getTeacher().getPersonalNumber());
            statement.setInt(4, newGroup.getEducationProgram().getIdEducationProgram());
            result = statement.executeUpdate();
            dataBaseConnection.closeConection();
        } else {
            result = -1;
        }
        return result;
    }

    @Override
    public int deleteGroup(int nrc) throws SQLException {
        int result = 0;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "DELETE FROM group_program WHERE nrc = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, nrc);
        result = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return result;
    }

    @Override
    public int modifyGroup(Group newGroup, int newNrc) throws SQLException {
        int result = 0;
        if ((newGroup.getNrc() == newNrc) || (checkAvailability(newNrc))) {
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection connection = dataBaseConnection.getConnection();
            String query = "UPDATE group_program SET nrc = ?, ee_id = ?, personal_number = ?, program_id = ? WHERE nrc = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, newNrc);
            statement.setInt(2, newGroup.getEe().getIdEe());
            statement.setInt(3, newGroup.getTeacher().getPersonalNumber());
            statement.setInt(4, newGroup.getEducationProgram().getIdEducationProgram());
            statement.setInt(5, newGroup.getNrc());
            result = statement.executeUpdate();
            dataBaseConnection.closeConection();
        } else {
            result = -1;
        }
        return result;
    }

    @Override
    public ArrayList<Group> getGroupsByEducationProgram(int idProgram) throws SQLException {
        ArrayList<Group> groups = new ArrayList<>();
        String query = "SELECT gp.period_id, gp.nrc, gp.personal_number, p.*, ee.ee_id, ee.name AS ee_name " +
                "FROM group_program gp " +
                "INNER JOIN teacher t ON t.personal_number = gp.personal_number " +
                "INNER JOIN person p ON P.person_id = T.person_id " +
                "INNER JOIN ee ee ON ee.ee_id = gp.ee_id " +
                "WHERE gp.program_id = ?";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProgram);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            EE ee = new EE();
            ee.setIdEe(resultSet.getInt("ee_id"));
            ee.setName(resultSet.getString("ee_name"));

            Teacher teacher = new Teacher();
            teacher.setPersonalNumber(resultSet.getInt("personal_number"));
            teacher.setIdPerson(resultSet.getInt("person_id"));
            teacher.setName(resultSet.getString("name"));
            teacher.setPaternalSurname(resultSet.getString("paternal_surname"));
            teacher.setMaternalSurname(resultSet.getString("maternal_surname"));

            Group group = new Group();
            group.setIdPeriod(resultSet.getInt("period_id"));
            group.setNrc(resultSet.getInt("nrc"));
            group.setEe(ee);
            group.setTeacher(teacher);
            groups.add(group);
        }
        dataBaseConnection.closeConection();
        return groups;
    }

    public boolean checkAvailability(int nrc) throws SQLException {
        boolean available = true;
        int result = 0;
        String query = "SELECT * FROM group_program WHERE nrc = ?";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, nrc);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            result++;
        }
        if (result > 0)
            available = false;
        dataBaseConnection.closeConection();
        return available;
    }
}

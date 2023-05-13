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
    public ArrayList<Group> groupsList(int idProgram, int idPeriod) throws SQLException {
        ArrayList<Group> groups = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = ("SELECT gp.nrc, ee.name as experience, concat(p.name, ' ', p.paternal_surname, ' ', p.maternal_surname) as teacher, " +
                "gp.personal_number, gp.ee_id, gp.program_id " +
                "FROM sistematutorias.group_program gp " +
                "INNER JOIN ee ON ee.ee_id = gp.ee_id " +
                "INNER JOIN teacher t ON t.personal_number = gp.personal_number " +
                "INNER JOIN person p ON p.person_id = t.person_id " +
                "WHERE gp.program_id = ? AND period_id = ? ;");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProgram);
        statement.setInt(2, idPeriod);
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
        if (checkAvailability(newGroup)) {
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection connection = dataBaseConnection.getConnection();
            String query = "INSERT INTO group_program (nrc, ee_id, personal_number, program_id, period_id) VALUES (?,?,?,?,?);";
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
    public int deleteGroup(int group_id) throws SQLException {
        int result = 0;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "UPDATE group_program SET active = 0 WHERE (group_id = ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, group_id);
        result = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return result;
    }

    @Override
    public int modifyGroup(Group newGroup, int newNrc) throws SQLException {
        int result = 0;
        if ((newGroup.getNrc() == newNrc) || (checkAvailability(newGroup))) {
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

    public boolean checkAvailability(Group newGroup) throws SQLException {
        boolean available = true;
        int result = 0;
        String query = "SELECT * FROM group_program WHERE nrc = ? AND period_id = ?";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, newGroup.getNrc());
        statement.setInt(2, newGroup.getPeriod());
        ResultSet resultSet = statement.executeQuery();
        dataBaseConnection.closeConection();
        if (resultSet.next()) {
            result++;
        }
        if (result > 0)
            available = false;
        return available;
    }
}

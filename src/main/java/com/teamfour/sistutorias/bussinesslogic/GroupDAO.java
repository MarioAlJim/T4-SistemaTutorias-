package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Group;

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
        String query = ("SELECT gp.nrc, ee.name as experience, concat(p.name, ' ', p.paternal_surname, ' ', p.maternal_surname) as teacher " +
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
                Group group = new Group();
                String experience = resultSet.getString("experience");
                String teacher = resultSet.getString("teacher");
                int nrc = resultSet.getInt("nrc");
                group.setExperience(experience);
                group.setTeacherName(teacher);
                group.setNrc(nrc);
                groups.add(group);
            } while (resultSet.next());
        }
        dataBaseConnection.closeConection();
        return groups;
    }
}

package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.UserRoleProgram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRoleProgramDAO implements IUserRoleProgramDAO {
    @Override
    public ArrayList<UserRoleProgram> getTutors() throws SQLException {
        ArrayList<UserRoleProgram> tutors = new ArrayList<>();
        String query = "SELECT P.name, P.paternal_surname, P.maternal_surname FROM user_program_role UPR " +
                "INNER JOIN user U ON U.email = UPR.email " +
                "INNER JOIN person P ON P.person_id = U.person_id " +
                "WHERE UPR.role_id = 1";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            tutors.add(getTutor(resultSet));
        }
        dataBaseConnection.closeConection();
        return tutors;
    }

    private UserRoleProgram getTutor(ResultSet resultSet) throws SQLException {
        UserRoleProgram tutor = new UserRoleProgram();
        String name = resultSet.getString("name");
        tutor.setName(name);
        String paternalSurname = resultSet.getString("paternal_surname");
        tutor.setPaternalSurname(paternalSurname);
        String maternalSurname = resultSet.getString("maternal_surname");
        tutor.setMaternalSurname(maternalSurname);
        return tutor;
    }

    @Override
    public ArrayList<UserRoleProgram> searchUser(String uvAcount, String password) throws SQLException {
        ArrayList<UserRoleProgram> users = new ArrayList<>();
        String query = ("SELECT upr.user_program_id, upr.program_id, upr.role_id, u.*, p.name, p.paternal_surname, p.maternal_surname " +
                "FROM sistematutorias.user_program_role upr " +
                "INNER JOIN sistematutorias.user u ON u.email = upr.email " +
                "INNER JOIN person p ON p.person_id = u.person_id "+
                "WHERE u.email = ? AND u.password = ?");
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, uvAcount);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        return users;
    }
}

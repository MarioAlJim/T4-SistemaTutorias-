package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO implements IUserDAO{
    public ArrayList<User> getTutors(int programId) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        ArrayList<User> tutors = new ArrayList<>();

        String query = "SELECT * FROM user " +
                "JOIN user_program_role ON user.email = user_program_role.email " +
                "JOIN person ON user.person_id = person.person_id " +
                "WHERE user_program_role.role_id = 1 AND user_program_role.program_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, programId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User tutor = new User();
            tutor.setEmail(resultSet.getString("email"));
            tutor.setName(resultSet.getString("name"));
            tutor.setPaternalSurname(resultSet.getString("paternal_surname"));
            tutor.setMaternalSurname(resultSet.getString("maternal_surname"));
            tutor.setIdPerson(resultSet.getInt("person_id"));
            tutors.add(tutor);
        }

        return tutors;
    }

    public ArrayList<User> getAllUsers() throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        ArrayList<User> users = new ArrayList<>();

        String query = "SELECT * FROM user\n" +
                "         JOIN user_program_role ON user.email = user_program_role.email\n" +
                "         JOIN person ON user.person_id = person.person_id\n" +
                "         JOIN sistem_role ON user_program_role.role_id = sistem_role.role_id;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            User user = new User();
            user.setEmail(resultSet.getString("email"));
            user.setName(resultSet.getString("name"));
            user.setPaternalSurname(resultSet.getString("paternal_surname"));
            user.setMaternalSurname(resultSet.getString("maternal_surname"));
            user.setIdPerson(resultSet.getInt("person_id"));
            user.addRole(resultSet.getString("description"));
            users.add(user);
        }
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            for (int j = i + 1; j < users.size(); j++) {
                User otherUser = users.get(j);
                if (user.getEmail().equals(otherUser.getEmail())) {
                    user.addRole(otherUser.getRoles());
                    users.remove(j);
                }
            }
        }
        return users;
    }
}

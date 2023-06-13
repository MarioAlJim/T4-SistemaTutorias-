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
        dataBaseConnection.closeConection();
        return tutors;
    }

    public ArrayList<User> getAllUsers() throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        ArrayList<User> users = new ArrayList<>();

        String query = "SELECT *, ep.name AS education_program FROM user\n" +
                "         JOIN user_program_role ON user.email = user_program_role.email\n" +
                "         JOIN person ON user.person_id = person.person_id\n" +
                "         JOIN sistem_role ON user_program_role.role_id = sistem_role.role_id" +
                "         JOIN education_program ep ON ep.education_program_id  = user_program_role.program_id;";
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
            user.setEducativeProgram(resultSet.getString("education_program"));
            users.add(user);
        }
        dataBaseConnection.closeConection();
        return users;
    }

    @Override
    public int insertUser(User user) throws SQLException {
        PersonDAO personDAO = new PersonDAO();
        int personId = personDAO.insertPerson(user);
        user.setIdPerson(personId);
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO user (email, password, person_id) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPassword());
        statement.setInt(3, user.getIdPerson());
        int columns = statement.executeUpdate();
        int result;
        if (columns == 1) {
            result = personId;
        } else {
            result = -1;
        }
        dataBaseConnection.closeConection();
        return result;
    }

    @Override
    public int updateUser(User user) throws SQLException {
        PersonDAO personDAO = new PersonDAO();
        int personId = personDAO.updatePerson(user);
        if (personId == -1) {
            return -1;
        } else {
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection connection = dataBaseConnection.getConnection();
            String query = "UPDATE user SET password = ? WHERE email = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, user.getPassword());
                statement.setString(2, user.getEmail());
                int columns = statement.executeUpdate();
                if (columns == 1) {
                    return personId;
                } else {
                    return -1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            } finally {
                dataBaseConnection.closeConection();
            }
        }
    }
}

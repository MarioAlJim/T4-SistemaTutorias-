package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.EducativeProgram;
import com.teamfour.sistutorias.domain.RoleProgram;
import com.teamfour.sistutorias.domain.UserRoleProgram;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.teamfour.sistutorias.dataaccess.SHA512;

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
        if(!resultSet.next()) {
            throw new SQLException("No se encontraron tutores");
        } else {
            do {
                tutors.add(getTutor(resultSet));
            } while (resultSet.next());
            dataBaseConnection.closeConection();
            return tutors;
        }
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
    public UserRoleProgram searchUser(String uvAcount, String password) throws SQLException {
        UserRoleProgram user = new UserRoleProgram();
        String query = ("SELECT U.email, P.name, P.paternal_surname, P.maternal_surname " +
                "FROM user U " +
                "INNER JOIN person P ON P.person_id = U.person_id " +
                "WHERE U.email = ? AND U.password = ?");
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, uvAcount);
        statement.setString(2, SHA512.getSHA512(password));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String email = resultSet.getString("email");
            String name = resultSet.getString("name");
            String paternalSurname = resultSet.getString("paternal_surname");
            String maternalSurname = resultSet.getString("maternal_surname");
            user.setName(name);
            user.setMaternalSurname(maternalSurname);
            user.setPaternalSurname(paternalSurname);
            user.setEmail(email);
            ArrayList<RoleProgram> rolePrograms;
            rolePrograms = getRoles(email);
            user.setRolesPrograms(rolePrograms);
        }
        dataBaseConnection.closeConection();
        return user;
    }

    private ArrayList<RoleProgram> getRoles(String email) throws SQLException {
        ArrayList<RoleProgram> rolePrograms = new ArrayList<>();
        String query = ("SELECT * FROM user_program_role UPR " +
                "INNER JOIN education_program EP ON UPR.program_id = EP.education_program_id " +
                "WHERE email = ?;");
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            do {
                RoleProgram roleProgram = new RoleProgram();
                EducativeProgram educativeProgram = new EducativeProgram();
                educativeProgram.setIdEducativeProgram(resultSet.getInt("program_id"));
                educativeProgram.setName(resultSet.getString("name"));
                roleProgram.setRole(resultSet.getInt("role_id"));
                roleProgram.setIdRoleProgram(resultSet.getInt("user_program_id"));
                roleProgram.setEducationProgram(educativeProgram);
                rolePrograms.add(roleProgram);
            } while (resultSet.next());
        }
        dataBaseConnection.closeConection();
        return rolePrograms;
    }

    @Override
    public ArrayList<UserRoleProgram> getTutorsByProgram(int idProgram) throws SQLException {
        ArrayList<UserRoleProgram> tutors = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT P.name, P.paternal_surname, P.maternal_surname, U.email FROM user_program_role UPR " +
                "INNER JOIN user U ON U.email = UPR.email " +
                "INNER JOIN person P ON P.person_id = U.person_id " +
                "WHERE UPR.role_id = 1 AND program_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProgram);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            do {
                UserRoleProgram tutor = new UserRoleProgram();
                String uvAcount = resultSet.getString("email");
                tutor.setEmail(uvAcount);
                String name = resultSet.getString("name");
                tutor.setName(name);
                String paternalSurname = resultSet.getString("paternal_surname");
                tutor.setPaternalSurname(paternalSurname);
                String maternalSurname = resultSet.getString("maternal_surname");
                tutor.setMaternalSurname(maternalSurname);
                tutors.add(tutor);
            } while (resultSet.next());
        }
        dataBaseConnection.closeConection();
        return tutors;
    }

    @Override
    public int insertRoleProgram(UserRoleProgram userRoleProgram) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO user_program_role (email, program_id, role_id) VALUES (?, ?, ?)";
        int result = 0;
        for (int i = 0; i < userRoleProgram.getRolesPrograms().size(); i++) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userRoleProgram.getEmail());
            statement.setInt(2, userRoleProgram.getRolesPrograms().get(i).getEducationProgram().getIdEducativeProgram());
            statement.setInt(3, userRoleProgram.getRolesPrograms().get(i).getRole());
            result += statement.executeUpdate();
        }
        dataBaseConnection.closeConection();
        return result;
    }

    @Override
    public int deleteRoleProgram(String email) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "DELETE FROM user_program_role WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        int result = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return result;
    }
}

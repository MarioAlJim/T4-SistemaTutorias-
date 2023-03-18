package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.User;
import com.teamfour.sistutorias.domain.UserRoleProgram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRoleProgramDAO implements IUserRoleProgramDAO {

    @Override
    public ArrayList<UserRoleProgram> searchUser(String uvAcount, String password) throws SQLException {
        ArrayList<UserRoleProgram> users = new ArrayList<>();
        String query = ("SELECT upr.user_program_id, upr.program_id, upr.role_id, u.*, p.name, p.paternal_surname, p.maternal_surname, ep.name as name_program " +
                "FROM sistematutorias.user_program_role upr " +
                "INNER JOIN sistematutorias.user u ON u.email = upr.email " +
                "INNER JOIN person p ON p.person_id = u.person_id " +
                "INNER JOIN education_program ep ON ep.education_program_id = upr.program_id " +
                "WHERE u.email = ? AND u.password = ?");
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, uvAcount);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            do {
                UserRoleProgram userRoleProgram = new UserRoleProgram();
                int idUserRoleProgram = resultSet.getInt("user_program_id");
                int idProgram = resultSet.getInt("program_id");
                String name = resultSet.getString("name");
                int role = resultSet.getInt("role_id");
                String paternalSurname = resultSet.getString("paternal_surname");
                String maternalSurnae = resultSet.getString("maternal_surname");
                String nameProgram = resultSet.getString("name_program");
                userRoleProgram.setIdRole(role);
                userRoleProgram.setIdProgram(idProgram);
                userRoleProgram.setName(name);
                userRoleProgram.setMaternalSurname(maternalSurnae);
                userRoleProgram.setPaternalSurname(paternalSurname);
                userRoleProgram.setProgram(nameProgram);
                userRoleProgram.setUserRoleProgram(idUserRoleProgram);
                users.add(userRoleProgram);
            }while (resultSet.next());
        }
        return users;
    }
}

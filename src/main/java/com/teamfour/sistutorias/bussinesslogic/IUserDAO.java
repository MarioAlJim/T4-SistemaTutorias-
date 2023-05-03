package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserDAO {
    ArrayList<User> getAllUsers() throws SQLException;

    int insertUser(User user) throws SQLException;

    int updateUser(User user) throws SQLException;
}

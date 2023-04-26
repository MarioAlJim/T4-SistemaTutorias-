package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserDAO {
    public ArrayList<User> getAllUsers() throws SQLException;

    public int insertUser(User user) throws SQLException;
}

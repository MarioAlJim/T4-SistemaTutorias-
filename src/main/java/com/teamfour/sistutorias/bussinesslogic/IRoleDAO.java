package com.teamfour.sistutorias.bussinesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import com.teamfour.sistutorias.domain.UserRoleProgram;

public interface IRoleDAO {

    public ArrayList<UserRoleProgram> searchUser(String uvAcount, String password) throws SQLException;;
}

package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.UserRoleProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserRoleProgramDAO {
    public ArrayList<UserRoleProgram> getTutors() throws SQLException;
}

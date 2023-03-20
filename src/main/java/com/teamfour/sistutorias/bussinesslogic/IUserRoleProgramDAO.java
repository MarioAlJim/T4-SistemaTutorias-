package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.UserRoleProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserRoleProgramDAO {
    public ArrayList<UserRoleProgram> getTutors() throws SQLException;
    public ArrayList<UserRoleProgram> searchUser(String uvAcount, String password) throws SQLException;
    public ArrayList<UserRoleProgram> getTutorsByProgram(int idProgram) throws SQLException;
    public ArrayList<UserRoleProgram> getTutorsByProgramName(String name,int idProgram) throws SQLException;
}

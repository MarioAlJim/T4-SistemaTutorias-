package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.EducativeProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IEducativeProgramDAO {
    public ArrayList<EducativeProgram> getEducativePrograms() throws SQLException;
    public int register(EducativeProgram educativeProgram) throws SQLException;
    public boolean updateEducativeProgram(EducativeProgram educativeProgram) throws SQLException;
}

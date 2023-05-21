package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.EducativeProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IEducationProgramDAO {
    public ArrayList<EducativeProgram> getEducationPrograms() throws SQLException;
    public int register(EducativeProgram educativeProgram) throws SQLException;
    public boolean updateEducationProgram(EducativeProgram educativeProgram) throws SQLException;
}

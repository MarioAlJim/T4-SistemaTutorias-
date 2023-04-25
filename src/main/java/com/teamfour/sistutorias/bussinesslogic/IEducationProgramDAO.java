package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.EducationProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IEducationProgramDAO {
    public ArrayList<EducationProgram> getEducationPrograms() throws SQLException;
    public int register(EducationProgram educationProgram) throws SQLException;
    public boolean updateEducationProgram(EducationProgram educationProgram) throws SQLException;
}
